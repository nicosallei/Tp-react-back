package tp.react.back.tpreactback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.react.back.tpreactback.modelo.Role;

public interface IRolRepository extends JpaRepository<Role, Long> {
}
