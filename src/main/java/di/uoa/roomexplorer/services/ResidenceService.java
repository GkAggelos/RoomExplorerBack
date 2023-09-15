package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.ResidenceNotFoundException;
import di.uoa.roomexplorer.model.*;
import di.uoa.roomexplorer.repositories.ReservationRepo;
import di.uoa.roomexplorer.repositories.ResidenceRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ResidenceService {

    private final ResidenceRepo residenceRepo;
    private final ReservationRepo reservationRepo;
    private final MatrixFactorizationService matrixFactorizationService;

    public ResidenceService(ResidenceRepo residenceRepo, ReservationRepo reservationRepo, MatrixFactorizationService matrixFactorizationService) {
        this.residenceRepo = residenceRepo;
        this.reservationRepo = reservationRepo;
        this.matrixFactorizationService = matrixFactorizationService;
    }

    public Residence addResidence(Residence newResidence) {
        matrixFactorizationService.addColumn();
        return residenceRepo.save(newResidence);
    }

    public List<Residence> findAllResidence() {
        return residenceRepo.findAll();
    }

    public  Residence findResidenceById(Long id) { return residenceRepo.findById(id)
            .orElseThrow(() -> new ResidenceNotFoundException("Residence by id " + id + " was not found")); }

    public Residence updateResidence(Residence newResidence) { return residenceRepo.save(newResidence); }

    public List<Residence> findResidencesByHostId(Long host_id) { return residenceRepo.findResidencesByHost_Id(host_id); }

    public Page<Residence> findResidencesByHostIdPagination(Long host_id, int page) {
        return residenceRepo.findResidencesByHost_Id(host_id, PageRequest.of(page, 10)); }

    public void deleteResidence(Long id) {
        residenceRepo.deleteById(id);
    }

    public Set<Photo> findAllPhotosByResidenceId(Long id) {
        Residence residence = findResidenceById(id);
        return residence.getPhotos();
    }

    public PageResponse<List<Residence>> findResidencesBySearchAndFilter(String city, LocalDate arrivalDate, LocalDate leaveDate,
                                                                         Integer peopleCapacity, String roomType, Boolean parking,
                                                                         Boolean livingRoom, Boolean wifi, Boolean heating,
                                                                         Boolean airCondition, Boolean cuisine, Boolean tv,
                                                                         Boolean elevator, String price, int page) {
        return residenceRepo.findResidencesBySearchAndFilter(city, arrivalDate, leaveDate, peopleCapacity,
                roomType, parking, livingRoom, wifi, heating, airCondition, cuisine, tv, elevator, price, page);
    }

//    public List<Residence> findResidenceRecommendations(Long renterId) {
//        List<Renter> renters = renterService.findAllRenters();
//        List<Residence> residences = this.findAllResidence();
//
//        Matrix R = Matrix.zero(renters.size(), residences.size());
//
//        int renterRow = -1;
//        for (int rent = 0; rent < renters.size(); rent++) {
//            if (renters.get(rent).getId().equals(renterId)) {
//                renterRow = rent;
//            }
//            int finalRent = rent;
//            List<Reservation> reservations = reservationRepo.findReservationsByRenter_Id(renters.get(rent).getId()).orElseThrow(
//                    ()-> new ResidenceNotFoundException("Reservations for renter with id" + renters.get(finalRent).getId() + "was not found")
//            );
//            for (int res = 0; res < residences.size(); res++) {
//                for (Reservation reservation : reservations) {
//                    if (reservation.getResidence().getId().equals(residences.get(res).getId())) {
//                        R.set(rent, res, reservation.getStars());
//                        break;
//                    }
//                }
//            }
//        }
//
//        MatrixFactorization matrixFactorization = new MatrixFactorization(R, 2, 0.001);
//        matrixFactorization.train();
//    //    List<Double> predictions = matrixFactorization.getPrediction(renterRow);
//        Vector predictions = matrixFactorization.getPrediction(renterRow);
//        List<Residence> recommendedResidences = new LinkedList<>();
//        List<Reservation> renterReservations = reservationRepo.findReservationsByRenter_Id(renterId).orElseThrow(
//                ()-> new ResidenceNotFoundException("Reservations for renter with id" + renterId + "was not found")
//        );
//        for (int i = 0; i < predictions.length(); i++) {
//            for (Reservation reservation : renterReservations) {
//                if (residences.get(i).getId().equals(reservation.getResidence().getId())) {
//                    predictions.set(i, -1.0);
//                    break;
//                }
//            }
//        }
//
//        for (int i = 0; i < 5; i++) {
//            double maxVal = predictions.max();
//            for (int j = 0; j < predictions.length(); j++) {
//                if (predictions.get(j) == maxVal) {
//                    recommendedResidences.add(residences.get(j));
//                    predictions.set(j, -1);
//                    break;
//                }
//            }
//        }
//
//        return recommendedResidences;
//    }
    public List<Residence> findResidenceRecommendations(Long renterId) {
        List<Long> reservedResidenceIds = reservationRepo.findReservedResidenceIdsByRenter_Id(renterId);
        List<Long> residencesId = residenceRepo.findAllResidenceId();

        List<Integer> reservedResidenceIndexes = new ArrayList<>();
        for (Long residenceId : reservedResidenceIds) {
            reservedResidenceIndexes.add(residencesId.indexOf(residenceId));
        }

        List<Integer> recommendedResidenceIndexes = matrixFactorizationService.getPredictions(renterId, reservedResidenceIndexes);
        List<Residence> recommendedResidences = new LinkedList<>();
        for (Integer residenceIndex : recommendedResidenceIndexes) {
            Residence residence = residenceRepo.findById(residencesId.get(residenceIndex)).orElseThrow();
            recommendedResidences.add(residence);
        }

        return recommendedResidences;
    }
}
