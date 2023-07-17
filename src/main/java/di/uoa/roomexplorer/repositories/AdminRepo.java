package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Long> {
}
