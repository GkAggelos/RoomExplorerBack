package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.repositories.RenterRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterService {
    private final RenterRepo renterRepo;

    public RenterService(RenterRepo renterRepo) {
        this.renterRepo = renterRepo;
    }
    public Renter addRenter(Renter newRenter) {
        return renterRepo.save(newRenter);
    }
    public List<Renter> findAllRenters() {
        return renterRepo.findAll();
    }
    public Renter findRenterById(Long id) {
        return renterRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }
    public Renter findRenterByUsername(String username) {
        return renterRepo.findRenterByUsername(username).orElseThrow(() -> new UserNotFoundException("User by username" + username + " was not found"));
    }
    public Renter updateRenter(Renter newrenter) {
        return renterRepo.save(newrenter);
    }
    public void deleteRenter(Long id) {
        renterRepo.deleteById(id);
    }
}
