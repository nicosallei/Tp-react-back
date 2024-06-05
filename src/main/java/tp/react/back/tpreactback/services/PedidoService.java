package tp.react.back.tpreactback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.react.back.tpreactback.modelo.Instrumento;
import tp.react.back.tpreactback.modelo.Pedido;
import tp.react.back.tpreactback.modelo.PedidoDetalle;
import tp.react.back.tpreactback.repository.IInstrumentoRepository;
import tp.react.back.tpreactback.repository.IPedidoRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepos;

    @Autowired
    private IInstrumentoRepository instrumentoRepos;

    public List<Pedido> traerListaPedidos(){
        List<Pedido> listaPedido = pedidoRepos.findAll();
        return listaPedido;
    }


    public Pedido traerPedido(long id){
        return pedidoRepos.findById(id).orElse(null);
    }

    public Pedido cargarPedido(Pedido pedido){
        double total=0;
        for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
            detalle.setPedido(pedido); // Asegurarte de que el PedidoDetalle tiene una referencia al Pedido
            Instrumento instrumento = detalle.getInstrumento();
            if (instrumento != null ) {
                Instrumento managedInstrumento = instrumentoRepos.findById(instrumento.getId());
                managedInstrumento.setCantidadVendida(managedInstrumento.getCantidadVendida() + detalle.getCantidad());
                instrumentoRepos.save(managedInstrumento);

                if (managedInstrumento != null) {
                    detalle.setInstrumento(managedInstrumento);
                    detalle.calcularSubtotal(); // Calcular el subtotal después de establecer el instrumento
                } else {
                    throw new RuntimeException("Instrumento no encontrado con ID: " + instrumento.getId());
                }
            }

            total += detalle.getSubtotal();
        }
        pedido.setTotalPedido(total);
        return pedidoRepos.save(pedido);
    }

    public void borrarPedido(long id){
        pedidoRepos.deleteById(id);
    }

    public Pedido modificarPedido(Pedido pedidoModificado){
        double total=0;
        Pedido pedidoOriginal = pedidoRepos.findById(pedidoModificado.getId()).orElse(null);
        if(pedidoOriginal != null){
            pedidoOriginal.getPedidoDetalle().removeIf(detalle ->
                    pedidoModificado.getPedidoDetalle().stream().noneMatch(modificadoDetalle ->
                            modificadoDetalle.getId() == detalle.getId()));

            for(PedidoDetalle modificadoDetalle : pedidoModificado.getPedidoDetalle()){
                PedidoDetalle originalDetalle = pedidoOriginal.getPedidoDetalle().stream()
                        .filter(detalle -> detalle.getId() == modificadoDetalle.getId())
                        .findFirst()
                        .orElse(null);

                if(originalDetalle != null){
                    originalDetalle.setCantidad(modificadoDetalle.getCantidad());
                    originalDetalle.calcularSubtotal(); // Calcular el subtotal después de establecer la cantidad
                } else {
                    Instrumento instrumento = instrumentoRepos.findById(modificadoDetalle.getInstrumento().getId());
                    if (instrumento != null) {
                        modificadoDetalle.setInstrumento(instrumento);
                        modificadoDetalle.setPedido(pedidoOriginal); // Establecer la entidad administrada como padre
                        pedidoOriginal.getPedidoDetalle().add(modificadoDetalle);
                    } else {
                        throw new RuntimeException("Instrumento no encontrado con ID: " + modificadoDetalle.getInstrumento().getId());
                    }
                }
            }

            for(PedidoDetalle detalle : pedidoOriginal.getPedidoDetalle()){
                total += detalle.getSubtotal();
            }
            pedidoOriginal.setTotalPedido(total);
            return pedidoRepos.save(pedidoOriginal);
        } else {
            throw new RuntimeException("Pedido no encontrado");
        }
    }


    public Map<String, Long> getPedidosGroupedByYearAndMonth() {
        List<Object[]> results = pedidoRepos.countPedidosGroupedByYearAndMonth();
        Map<String, Long> pedidosGroupedByYearAndMonth = new HashMap<>();
        for (Object[] result : results) {
            String yearAndMonth = result[0] + "-" + result[1];
            Long count = (Long) result[2];
            pedidosGroupedByYearAndMonth.put(yearAndMonth, count);
        }
        return pedidosGroupedByYearAndMonth;
    }


public Map<String, Long> getPedidosGroupedByWeek() {
    List<Object[]> results = pedidoRepos.countPedidosGroupedByYearMonthAndWeek();
    Map<String, Long> pedidosGroupedByWeek = new HashMap<>();
    for (Object[] result : results) {
        String yearMonthAndWeek = result[0] + "-" + result[1] + "-W" + result[2];
        Long count = (Long) result[3];
        pedidosGroupedByWeek.put(yearMonthAndWeek, count);
    }
    return pedidosGroupedByWeek;
}


    public Map<String, Long> getPedidosGroupedByInstrumento() {
        List<Object[]> results = pedidoRepos.countPedidosGroupedByInstrumento();
        Map<String, Long> pedidosGroupedByInstrumento = new HashMap<>();
        for (Object[] result : results) {
            String instrumento = (String) result[0];
            Long count = (Long) result[1];
            pedidosGroupedByInstrumento.put(instrumento, count);
        }
        return pedidosGroupedByInstrumento;
    }

    public List<Pedido> getAllPedidosBetween(LocalDate fechaDesde, LocalDate fechaHasta) {
        return pedidoRepos.findByFechaBetween(fechaDesde, fechaHasta);
    }
}
