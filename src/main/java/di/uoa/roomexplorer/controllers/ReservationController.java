package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.MessageResponse;
import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.services.ReservationService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    @RolesAllowed({"renter"})
    public ResponseEntity<MessageResponse> addReservation(@RequestBody Reservation newReservation) {
        MessageResponse message = reservationService.addReservation(newReservation);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @RolesAllowed({"admin"})
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    @RolesAllowed({"renter"})
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/find/residence/{id}")
    @RolesAllowed({"host", "admin"})
    public ResponseEntity<List<Reservation>> getReservationsByResidence_id(@PathVariable("id") Long residence_id) {
        List<Reservation> reservations = reservationService.findReservationByResidence(residence_id);

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/find/residence/{id}/{page}")
    public PageResponse<Page<Reservation>> getReservationsByResidence_idPagination(@PathVariable("id") Long residence_id, @PathVariable("page") int page) {
        Page<Reservation> reservations = reservationService.findReservationByResidencePagination(residence_id, page);

        return new PageResponse<>(reservations.getTotalElements(),reservations);
    }

    @GetMapping("/find/renter/{id}")
    @RolesAllowed({"renter", "admin"})
    public ResponseEntity<List<Reservation>> getReservationsByRenter_id(@PathVariable("id") Long renter_id) {
        List<Reservation> reservations = reservationService.findReservationsByRenter(renter_id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/find/renter/{id}/{page}")
    @RolesAllowed({"renter"})
    public PageResponse<Page<Reservation>> getReservationsByRenter_idPagination(@PathVariable("id") Long renter_id, @PathVariable("page") int page) {
        Page<Reservation> reservations = reservationService.findReservationsByRenterPagination(renter_id, page);

        return new PageResponse<>(reservations.getTotalElements(), reservations);
    }

    @PutMapping("/update")
    @RolesAllowed({"host", "renter"})
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation newReservation) {
        Reservation reservation = reservationService.updateReservation(newReservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"renter", "host"})
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/host/{id}")
    @RolesAllowed({"admin"})
    public ResponseEntity<List<Reservation>> getReservationsByHost_id(@PathVariable("id") Long host_id) {
        List<Reservation> reservations = reservationService.findReservationsByHost(host_id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
