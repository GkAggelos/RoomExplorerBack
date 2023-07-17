package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenterRepo extends JpaRepository<Renter, Long> {
}
