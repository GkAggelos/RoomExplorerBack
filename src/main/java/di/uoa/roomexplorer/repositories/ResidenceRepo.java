package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ResidenceRepo extends JpaRepository<Residence, Long> {

    List<Residence> findResidencesByHost_Id(Long id);

    @Query("SELECT r FROM Residence r WHERE r.peopleCapacity>=?1 AND " +
//            "LOWER(r.location) LIKE LOWER(CONCAT('%', CONCAT(?2,'%'))) AND " +
            "(LOCATE(LOWER(r.location), LOWER(?2)) > 0) AND " +
            "r.available_from < ?3 AND r.available_till > ?4")

        // NOTE: must arrivalDate < leaveDate check in frontend
    List<Residence> findResidencesBySearch(Integer peopleCapacity, String location, LocalDate arrivalDate, LocalDate leaveDate);
}
