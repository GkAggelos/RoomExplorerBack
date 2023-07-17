package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.ReservationNotFoundException;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.repositories.ReservationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;

    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public Reservation addReservation(Reservation newReservation) {
        return reservationRepo.save(newReservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepo.findAll();
    }

    public Reservation findReservationById(Long id) {
        return reservationRepo.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation by id " + id + " was not found"));
    }
    public List<Reservation> findReservationByResidence(Long residence_id) {
        return reservationRepo.findReservationsByResidence_Id(residence_id).
                orElseThrow(() -> new ReservationNotFoundException("Reservations for residence by id " + residence_id + " were not found"));
    }
    public List<Reservation> findReservationByRenter(Long renter_id) {
        return reservationRepo.findReservationsByRenter_Id(renter_id).
                orElseThrow(() -> new ReservationNotFoundException("Reservations for renter by id " + renter_id + " were not found"));
    }

    public Reservation updateReservation(Reservation newReservation) {
        return reservationRepo.save(newReservation);
    }

    public void deleteReservationById(Long id) {
        reservationRepo.deleteById(id);
    }
}
