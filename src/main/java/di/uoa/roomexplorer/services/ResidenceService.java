package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.ReservationNotFoundException;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.repositories.ResidenceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidenceService {
    private final ResidenceRepo residenceRepo;

    public ResidenceService(ResidenceRepo residenceRepo) {
        this.residenceRepo = residenceRepo;
    }

    public Residence addResidence(Residence newResidence) {
        return residenceRepo.save(newResidence);
    }

    public List<Residence> findAllResidence() {
        return residenceRepo.findAll();
    }

    public List<Reservation> findReservationsByHostId(Long host_id) {
        return residenceRepo.findReservationsByHostId(host_id).
                orElseThrow(() -> new ReservationNotFoundException("Reservations for host by id " + host_id + " were not found"));
    }
}
