package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepo extends JpaRepository<Host, Long> {
    Optional<Host> findHostByUsername(String username);
}
