package tp.react.back.tpreactback.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario extends EntityId  {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}