package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.services.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/residence")
public class ResidenceController {
    private final ResidenceService residenceService;

    @Autowired
    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    @PostMapping("/add")
    public ResponseEntity<Residence> addResidence(@RequestBody Residence newResidence) {
        Residence residence = residenceService.addResidence(newResidence);
        return new ResponseEntity<>(residence, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Residence>> getAllResidences() {
        List<Residence> residences = residenceService.findAllResidence();
        return new ResponseEntity<>(residences, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Residence> getResidenceById(@PathVariable("id") Long id) {
        Residence residence = residenceService.findResidenceById(id);
        return new ResponseEntity<>(residence, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/find/host/{id}")
    public ResponseEntity<List<Residence>> getResidenceByHostId(@PathVariable("id") Long host_id) {
        List<Residence> residences = residenceService.findResidencesByHostId(host_id);
        return new ResponseEntity<>(residences, HttpStatus.OK);
    }

    @GetMapping("/find/photos/{id}")
    public ResponseEntity<Set<Photo>> getPhotosByResidenceId(@PathVariable("id") Long residence_id) {
        Set<Photo> photos = residenceService.findAllPhotosByResidenceId(residence_id);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Residence> updateResidence(@RequestBody Residence newResidence) {
        Residence residence = residenceService.updateResidence(newResidence);
        return new ResponseEntity<>(residence, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResidence(@PathVariable("id") Long residence_id) {
        residenceService.deleteResidence(residence_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Residence>> getResidencesBySearch(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate leaveDate,
            @RequestParam Integer peopleCapacity) {

        List<Residence> residences = residenceService.findResidencesBySearch(location, arrivalDate, leaveDate, peopleCapacity);
        return new ResponseEntity<>(residences, HttpStatus.OK);
    }

//    @GetMapping("/find/host/{id}/reservations")
//    public ResponseEntity<List<Reservation>> getReservationByHostId(@PathVariable("id") Long id) {
//        List<Reservation> reservations = residenceService.findReservationsByHostId(id);
//        return new ResponseEntity<>(reservations, HttpStatus.OK);
//    }
}
