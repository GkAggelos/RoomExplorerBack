package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    public Optional<List<Reservation>> findReservationsByResidence_Id(Long residenceId);

    public Optional<List<Reservation>> findReservationsByRenter_Id(Long renterId);
}
