package tp.react.back.tpreactback.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Instrumento {
    @Id
    private long id;
    private String instrumento;
    private String marca;
    private String modelo;
    private String imagen;
    private String precio;
    private String costoEnvio;
    private String cantidadVendida;
    private String descripcion;
}
