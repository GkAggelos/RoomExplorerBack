package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.repositories.RenterRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<String> findAllUsernames() {
        List<Renter> renters = findAllRenters();
        List<String> usernames = new ArrayList<String>();
        for (int i = 0; i < renters.size(); i++) {
            usernames.add(renters.get(i).getUsername());
        }
        return usernames;
    }

    public List<String> findAllEmails() {
        List<Renter> renters = findAllRenters();
        List<String> emails  = new ArrayList<String>();
        for (int i = 0; i < renters.size(); i++) {
            emails.add(renters.get(i).getEmail());
        }
        return emails;
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
