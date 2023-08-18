package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.MessageResponse;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<MessageResponse> addReservation(@RequestBody Reservation newReservation) {
        MessageResponse message = reservationService.addReservation(newReservation);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/find/residence/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByResidence_id(@PathVariable("id") Long residence_id) {
        List<Reservation> reservations = reservationService.findReservationByResidence(residence_id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/find/renter/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByRenter_id(@PathVariable("id") Long renter_id) {
        List<Reservation> reservations = reservationService.findReservationByRenter(renter_id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation newReservation) {
        Reservation reservation = reservationService.updateReservation(newReservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/host/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByHost_id(@PathVariable("id") Long host_id) {
        List<Reservation> reservations = reservationService.findReservationsByHost(host_id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
