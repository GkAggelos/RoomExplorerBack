package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.AvailablePeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailablePeriodRepo extends JpaRepository<AvailablePeriod, Long> {

    List<AvailablePeriod> findAvailablePeriodsByResidenceId(Long residenceId);
}
