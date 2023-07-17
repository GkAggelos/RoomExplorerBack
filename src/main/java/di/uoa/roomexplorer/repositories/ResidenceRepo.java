package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepo extends JpaRepository<Residence, Long> {

    Optional<List<Reservation>> findReservationsByHostId(Long id);
}
