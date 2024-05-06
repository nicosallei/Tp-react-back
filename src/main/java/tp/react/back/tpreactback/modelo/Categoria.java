package tp.react.back.tpreactback.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class Categoria extends EntityId{

    private String denominacion;

    @Column(unique = true)
    private long codigo;



}
