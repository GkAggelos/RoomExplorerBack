package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Residence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResidenceRepo extends JpaRepository<Residence, Long>, CustomResidenceRepo {

    List<Residence> findResidencesByHost_Id(Long id);
    Page<Residence> findResidencesByHost_Id(Long id, Pageable pageable);

    @Query("SELECT r.id FROM Residence r")
    List<Long> findAllResidenceId();
}
