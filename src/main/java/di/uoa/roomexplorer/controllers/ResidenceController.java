package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.services.ResidenceService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @RolesAllowed({"host"})
    public ResponseEntity<Residence> addResidence(@RequestBody Residence newResidence) {
        Residence residence = residenceService.addResidence(newResidence);
        return new ResponseEntity<>(residence, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @RolesAllowed({"admin"})
    public ResponseEntity<List<Residence>> getAllResidences() {
        List<Residence> residences = residenceService.findAllResidence();
        return new ResponseEntity<>(residences, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Residence> getResidenceById(@PathVariable("id") Long id) {
        Residence residence = residenceService.findResidenceById(id);
        return new ResponseEntity<>(residence, HttpStatus.OK);
    }

    @GetMapping("/find/host/{id}")
    @RolesAllowed({"admin", "host"})
    public ResponseEntity<List<Residence>> getResidenceByHostId(@PathVariable("id") Long host_id) {
        List<Residence> residences = residenceService.findResidencesByHostId(host_id);
        return new ResponseEntity<>(residences, HttpStatus.OK);
    }

    @GetMapping("/find/host/{id}/{page}")
    @RolesAllowed({"host", "admin"})
    public PageResponse<Page<Residence>> getResidenceByHostIdPagination(@PathVariable("id") Long host_id, @PathVariable("page") int page) {
        Page<Residence> residences = residenceService.findResidencesByHostIdPagination(host_id, page);
        return new PageResponse<>(residences.getTotalElements(), residences);
    }

    @GetMapping("/find/photos/{id}")
    public ResponseEntity<Set<Photo>> getPhotosByResidenceId(@PathVariable("id") Long residence_id) {
        Set<Photo> photos = residenceService.findAllPhotosByResidenceId(residence_id);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @PutMapping("/update")
    @RolesAllowed({"host"})
    public ResponseEntity<Residence> updateResidence(@RequestBody Residence newResidence) {
        Residence residence = residenceService.updateResidence(newResidence);
        return new ResponseEntity<>(residence, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"host"})
    public ResponseEntity<?> deleteResidence(@PathVariable("id") Long residence_id) {
        residenceService.deleteResidence(residence_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/filter/{page}")
    public PageResponse<List<Residence>> getResidencesBySearchAndFilter(
            @RequestParam String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate leaveDate,
            @RequestParam Integer peopleCapacity,
            @RequestParam String roomType,
            @RequestParam Boolean parking, @RequestParam Boolean livingRoom,
            @RequestParam Boolean wifi, @RequestParam Boolean heating,
            @RequestParam Boolean airCondition, @RequestParam Boolean cuisine,
            @RequestParam Boolean tv, @RequestParam Boolean elevator,
            @RequestParam String price, @PathVariable("page") int page) {

        return residenceService.findResidencesBySearchAndFilter(city, arrivalDate, leaveDate, peopleCapacity,
                                                                roomType, parking, livingRoom, wifi,
                                                                heating, airCondition, cuisine, tv, elevator, price, page);
    }
}
