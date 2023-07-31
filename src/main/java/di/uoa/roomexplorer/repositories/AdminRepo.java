package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByUsername(String username);
}
