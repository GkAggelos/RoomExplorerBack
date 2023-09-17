package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RenterRepo extends JpaRepository<Renter, Long> {
    Optional<Renter> findRenterByUsername(String username);

    @Query("SELECT r.id FROM Renter r ORDER BY r.id")
    List<Long> findAllRenterId();
}
