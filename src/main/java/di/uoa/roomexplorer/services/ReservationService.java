package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.ReservationNotFoundException;
import di.uoa.roomexplorer.model.MessageResponse;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.ReservationState;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.repositories.ReservationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final ResidenceService residenceService;
    private final MatrixFactorizationService matrixFactorizationService;

    public ReservationService(ReservationRepo reservationRepo, ResidenceService residenceService, MatrixFactorizationService matrixFactorizationService) {
        this.reservationRepo = reservationRepo;
        this.residenceService = residenceService;
        this.matrixFactorizationService = matrixFactorizationService;
    }

    public MessageResponse addReservation(Reservation newReservation) {
        if (newReservation.getArrivalDate().isBefore(newReservation.getResidence().getAvailable_from()) ||
                newReservation.getLeaveDate().isAfter(newReservation.getResidence().getAvailable_till())) {
            return MessageResponse.builder()
                    .message("error not available")
                    .build();
        }

        List<Reservation> reservations = findReservationByResidence(newReservation.getResidence().getId());
        for (Reservation reservation : reservations) {
            if ((reservation.getLeaveDate().equals(newReservation.getLeaveDate()) || reservation.getArrivalDate().equals(newReservation.getArrivalDate())
                 || (newReservation.getLeaveDate().isAfter(reservation.getArrivalDate()) && newReservation.getLeaveDate().isBefore(reservation.getLeaveDate()))
                 || (newReservation.getArrivalDate().isAfter(reservation.getArrivalDate()) && newReservation.getArrivalDate().isBefore(reservation.getLeaveDate()))) &&
                    (reservation.getState().equals(ReservationState.PENDING) || reservation.getState().equals(ReservationState.ACCEPTED))) {
                if (reservation.getRenter().getId().equals(newReservation.getRenter().getId())) {
                    return MessageResponse.builder()
                            .message("error already book")
                            .build();
                }
                else {
                    return MessageResponse.builder()
                            .message("error not available")
                            .build();
                }
            }
        }
        reservationRepo.save(newReservation);
        return MessageResponse.builder()
                .message("ok")
                .build();
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

    public Page<Reservation> findReservationByResidencePagination(Long residence_id, int page) {
        return reservationRepo.findReservationsByResidence_Id(residence_id, PageRequest.of(page, 10)).
                orElseThrow(() -> new ReservationNotFoundException("Reservations for residence by id " + residence_id + " were not found"));
    }
    public List<Reservation> findReservationsByRenter(Long renter_id) {
        return reservationRepo.findReservationsByRenter_Id(renter_id).
                orElseThrow(() -> new ReservationNotFoundException("Reservations for renter by id " + renter_id + " were not found"));
    }

    public Page<Reservation> findReservationsByRenterPagination(Long renter_id, int page) {
        return reservationRepo.findReservationsByRenter_Id(renter_id, PageRequest.of(page, 10)).
                orElseThrow(() -> new ReservationNotFoundException("Reservations for renter by id " + renter_id + " were not found"));
    }

    public List<Reservation> findReservationsByHost(Long host_id) {
        List<Residence> residences = residenceService.findResidencesByHostId(host_id);
        List<Reservation> reservations = new LinkedList<>();
        for (Residence residence: residences) {
            reservations.addAll(this.findReservationByResidence(residence.getId()));
        }
        return reservations;
    }

    public Reservation updateReservation(Reservation newReservation) {
        Reservation reservation = findReservationById(newReservation.getId());

        int star_sum = reservation.getResidence().getStarsAverage() * reservation.getResidence().getReviewsNumber();
        if (reservation.getReview() != null && reservation.getReview().equals("") && !newReservation.getReview().equals("")) {
            star_sum = star_sum + newReservation.getStars();
            int average = star_sum / (reservation.getResidence().getReviewsNumber() + 1);
            newReservation.getResidence().setStarsAverage(average);
            newReservation.getResidence().setReviewsNumber(reservation.getResidence().getReviewsNumber() + 1);
            matrixFactorizationService.updateCellId(newReservation.getRenter().getId(), newReservation.getResidence().getId(), newReservation.getStars());
            matrixFactorizationService.train();
        }
        else if (reservation.getReview() != null && !newReservation.getReview().equals("")){
            star_sum = star_sum - reservation.getStars() + newReservation.getStars();
            int average = star_sum / reservation.getResidence().getReviewsNumber();
            newReservation.getResidence().setStarsAverage(average);
            matrixFactorizationService.updateCellId(newReservation.getRenter().getId(), newReservation.getResidence().getId(), newReservation.getStars());
            matrixFactorizationService.train();
        }

        residenceService.updateResidence(newReservation.getResidence());
        return reservationRepo.save(newReservation);
    }

    public void deleteReservationById(Long id) {
        reservationRepo.deleteById(id);
    }
}
