package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.services.RenterService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/renter")
public class RenterController {

    private final RenterService renterService;

    @Autowired
    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    @GetMapping("/all/{page}")
    @RolesAllowed({"admin"})
    public PageResponse<Page<Renter>> getAllRentersPagination(@PathVariable("page") int page) {
        Page<Renter> renters = renterService.findAllRentersPagination(page);
        return new PageResponse<>(renters.getTotalElements(), renters);
    }

    @GetMapping("/find")
    @RolesAllowed({"admin", "renter"})
    public ResponseEntity<Renter> getRenterById(@RequestParam Long id) {
        Renter renter = renterService.findRenterById(id);
        return new ResponseEntity<>(renter, HttpStatus.OK);
    }

    @GetMapping("/find/all/usernames")
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = renterService.findAllUsernames();
        return new ResponseEntity<>(usernames,HttpStatus.OK);
    }

    @GetMapping("/find/all/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = renterService.findAllEmails();
        return new ResponseEntity<>(emails,HttpStatus.OK);
    }
}
