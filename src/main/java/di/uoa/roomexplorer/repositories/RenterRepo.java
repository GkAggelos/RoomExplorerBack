package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RenterRepo extends JpaRepository<Renter, Long> {
    Optional<Renter> findRenterByUsername(String username);
}
