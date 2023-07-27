package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.model.AvailablePeriod;
import di.uoa.roomexplorer.repositories.AvailablePeriodRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvailablePeriodService {

    private final AvailablePeriodRepo availablePeriodRepo;

    public AvailablePeriodService(AvailablePeriodRepo availablePeriodRepo) {
        this.availablePeriodRepo = availablePeriodRepo;
    }

    public List<AvailablePeriod> addAvailablePeriods(List<AvailablePeriod> availablePeriods) {

        List<AvailablePeriod> periods = new ArrayList<>();

        for (AvailablePeriod period : availablePeriods) {
            periods.add(availablePeriodRepo.save(period));
        }

        return periods;
    }

    public List<AvailablePeriod> getAvailablePeriodsByResidenceId(Long residenceId) {
        return availablePeriodRepo.findAvailablePeriodsByResidenceId(residenceId);
    }
}
