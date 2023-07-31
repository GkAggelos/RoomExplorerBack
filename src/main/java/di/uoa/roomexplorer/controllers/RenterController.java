package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.services.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public ResponseEntity<List<Renter>> getAllRenters() {
        List<Renter> renters = renterService.findAllRenters();
        return new ResponseEntity<>(renters, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Renter> getRenterById(@PathVariable("id") Long id) {
        Renter renter = renterService.findRenterById(id);
        return new ResponseEntity<>(renter, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/find/all/usernames")
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = renterService.findAllUsernames();
        return new ResponseEntity<>(usernames,HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/find/all/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = renterService.findAllEmails();
        return new ResponseEntity<>(emails,HttpStatus.OK);
    }

    @GetMapping("/find/username/{username}")
    public ResponseEntity<Renter> getRenterByUsername(@PathVariable("username") String username) {
        Renter renter = renterService.findRenterByUsername(username);
        return new ResponseEntity<>(renter, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Renter> addRenter(@RequestBody Renter newrenter) {
        Renter renter = renterService.addRenter(newrenter);
        return new ResponseEntity<>(renter, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Renter> updateRenter(@RequestBody Renter newrenter) {
        Renter renter = renterService.updateRenter(newrenter);
        return new ResponseEntity<>(renter, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Renter> deleteRenter(@PathVariable("id") Long id) {
        renterService.deleteRenter(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
