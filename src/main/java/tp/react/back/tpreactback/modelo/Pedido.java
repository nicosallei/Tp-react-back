package tp.react.back.tpreactback.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido extends EntityId{

private Date fecha;
private Double totalPedido;

@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
@JsonManagedReference
private List<PedidoDetalle> pedidoDetalle;



}
