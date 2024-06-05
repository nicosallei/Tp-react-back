package tp.react.back.tpreactback.modelo;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Data
public class Instrumento extends EntityId{

    @Column(length = 2000)
    private String instrumento;
    private String marca;
    private String modelo;
    @Column(length = 10000)
    private String imagen;
    private double precio;
    private String costoEnvio;
    private long cantidadVendida;
    @Column(length = 2000)
    private String descripcion;
    @Transient // Este campo no se guardará en la base de datos
    private MultipartFile imagenFile;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
