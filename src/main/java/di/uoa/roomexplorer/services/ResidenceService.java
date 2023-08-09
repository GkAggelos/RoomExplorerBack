package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.ReservationNotFoundException;
import di.uoa.roomexplorer.exception.ResidenceNotFoundException;
import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.repositories.ResidenceRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    public  Residence findResidenceById(Long id) { return residenceRepo.findById(id)
            .orElseThrow(() -> new ResidenceNotFoundException("Residence by id " + id + " was not found")); }

    public Residence updateResidence(Residence newResidence) { return residenceRepo.save(newResidence); }

    public List<Residence> findResidencesByHostId(Long host_id) { return residenceRepo.findResidencesByHost_Id(host_id); }

    public void deleteResidence(Long id) {
        residenceRepo.deleteById(id);
    }

    public Set<Photo> findAllPhotosByResidenceId(Long id) {
        Residence residence = findResidenceById(id);
        return residence.getPhotos();
    }

    public List<Residence> findResidencesBySearch(String location, LocalDate arrivalDate, LocalDate leaveDate, int peopleCapacity) {
        return residenceRepo.findResidencesBySearch(peopleCapacity, location, arrivalDate, leaveDate);
    }

//    public List<Reservation> findReservationsByHostId(Long host_id) {
//        return residenceRepo.findReservationsByHostId(host_id).
//                orElseThrow(() -> new ReservationNotFoundException("Reservations for host by id " + host_id + " were not found"));
//    }
}
