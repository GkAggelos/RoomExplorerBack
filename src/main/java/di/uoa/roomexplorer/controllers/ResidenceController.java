package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.services.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/find/host/{id}/reservations/")
    public ResponseEntity<List<Reservation>> getReservationByHostId(@PathVariable("id") Long id) {
        List<Reservation> reservations = residenceService.findReservationsByHostId(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
