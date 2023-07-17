package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepo extends JpaRepository<Host, Long> {
}
