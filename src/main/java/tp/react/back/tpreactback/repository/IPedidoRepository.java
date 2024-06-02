package tp.react.back.tpreactback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tp.react.back.tpreactback.modelo.Pedido;
import tp.react.back.tpreactback.modelo.PedidoDetalle;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT YEAR(p.fecha) as year, MONTH(p.fecha) as month, COUNT(p) as count " +
            "FROM Pedido p GROUP BY YEAR(p.fecha), MONTH(p.fecha)")
    List<Object[]> countPedidosGroupedByYearAndMonth();

    @Query("SELECT pd.instrumento.instrumento, COUNT(pd) as count " +
            "FROM PedidoDetalle pd GROUP BY pd.instrumento.instrumento")
    List<Object[]> countPedidosGroupedByInstrumento();

    @Query("SELECT pd FROM PedidoDetalle pd WHERE FUNCTION('DATE', pd.pedido.fecha) BETWEEN :fechaDesde AND :fechaHasta")
    List<PedidoDetalle> findPedidosBetweenDates(LocalDate fechaDesde, LocalDate fechaHasta);


}
