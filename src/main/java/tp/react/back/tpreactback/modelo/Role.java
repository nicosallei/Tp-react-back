package tp.react.back.tpreactback.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Role extends EntityId{

    @Column(nullable = false, unique = true)
    private String name;

}
