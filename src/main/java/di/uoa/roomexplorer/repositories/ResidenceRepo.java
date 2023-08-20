package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Residence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidenceRepo extends JpaRepository<Residence, Long>, CustomResidenceRepo {

    List<Residence> findResidencesByHost_Id(Long id);
    Page<Residence> findResidencesByHost_Id(Long id, Pageable pageable);
}
