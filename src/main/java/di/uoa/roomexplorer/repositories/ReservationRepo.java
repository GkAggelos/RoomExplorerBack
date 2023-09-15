package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    Optional<Page<Reservation>> findReservationsByResidence_Id(Long residenceId, Pageable pageable);

    Optional<List<Reservation>> findReservationsByResidence_Id(Long residenceId);

    Optional<List<Reservation>> findReservationsByRenter_Id(Long renterId);

    @Query("SELECT r.residence.id FROM Reservation r WHERE r.renter.id = ?1")
    List<Long> findReservedResidenceIdsByRenter_Id(Long renterId);

    Optional<Page<Reservation>> findReservationsByRenter_Id(Long renterId, Pageable pageable);
}
