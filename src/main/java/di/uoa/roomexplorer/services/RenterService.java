package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.repositories.RenterRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RenterService {
    private final RenterRepo renterRepo;
    private final MatrixFactorizationService matrixFactorizationService;

    public RenterService(RenterRepo renterRepo, MatrixFactorizationService matrixFactorizationService) {
        this.renterRepo = renterRepo;
        this.matrixFactorizationService = matrixFactorizationService;
    }

    public Renter addRenter(Renter newRenter) {
        newRenter = renterRepo.save(newRenter);
        int newRenterIndex = renterRepo.findAllRenterId().indexOf(newRenter.getId());
        matrixFactorizationService.addRow(newRenterIndex);

        return newRenter;
    }

    public List<Renter> findAllRenters() {
        return renterRepo.findAll();
    }

    public Page<Renter> findAllRentersPagination(int page) {
        return renterRepo.findAll(PageRequest.of(page, 10));
    }

    public List<String> findAllUsernames() {
        List<Renter> renters = findAllRenters();
        List<String> usernames = new ArrayList<>();
        for (Renter renter : renters) {
            usernames.add(renter.getUsername());
        }
        return usernames;
    }

    public List<String> findAllEmails() {
        List<Renter> renters = findAllRenters();
        List<String> emails  = new ArrayList<>();
        for (Renter renter : renters) {
            emails.add(renter.getEmail());
        }
        return emails;
    }
    public Renter findRenterById(Long id) {
        return renterRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

}
