package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ResidenceRepo extends JpaRepository<Residence, Long>, CustomResidenceRepo {

    List<Residence> findResidencesByHost_Id(Long id);
}
