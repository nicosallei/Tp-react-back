package tp.react.back.tpreactback.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PedidoDetalle extends EntityId{

    private int cantidad;
    private double subtotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instrumento_id")
    private Instrumento instrumento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;


    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        if (instrumento != null  && cantidad > 0) {
            subtotal = instrumento.getPrecio() * cantidad;
        }
    }

}
