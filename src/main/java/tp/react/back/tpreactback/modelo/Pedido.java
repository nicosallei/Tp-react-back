package tp.react.back.tpreactback.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Pedido extends EntityId{

    @Column(columnDefinition = "DATE")
    private LocalDate fecha;
    private Double totalPedido;
    private String titulo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PedidoDetalle> pedidoDetalle;
}