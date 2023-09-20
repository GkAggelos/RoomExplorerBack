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
    private final SearchService searchService;

    public ResidenceService(ResidenceRepo residenceRepo, ReservationRepo reservationRepo, MatrixFactorizationService matrixFactorizationService, SearchService searchService) {
        this.residenceRepo = residenceRepo;
        this.reservationRepo = reservationRepo;
        this.matrixFactorizationService = matrixFactorizationService;
        this.searchService = searchService;
    }

    public Residence addResidence(Residence newResidence) {
        newResidence = residenceRepo.save(newResidence);
        int residenceIndex = residenceRepo.findAllResidenceId().indexOf(newResidence.getId());
        this.matrixFactorizationService.addColumn(residenceIndex);
        System.out.println(residenceIndex);
        return newResidence;
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


    public List<Residence> findResidenceRecommendations(Long renterId) {

        boolean hasReservations = false;
        List<Long> reservedResidenceIds = new ArrayList<>();
        if (reservationRepo.countNonEmptyReservationsByRenter_Id(renterId) > 0) {
            reservedResidenceIds = reservationRepo.findReservedResidenceIdsByRenter_Id(renterId);
            hasReservations = true;
        }

        // If there are no reservations, get residences from search history
        if (!hasReservations) {
            List<Long> searchedResidenceIds = searchService.findResidenceIdsByRenterId(renterId);
            for (Long searchedResidenceId : searchedResidenceIds) {
                matrixFactorizationService.updateCellId(renterId, searchedResidenceId, 3);
            }
            matrixFactorizationService.train();
            // revert changes
            for (Long searchedResidenceId : searchedResidenceIds) {
                matrixFactorizationService.updateCellId(renterId, searchedResidenceId, 0);
            }
            matrixFactorizationService.train();
//            reservedResidenceIds = searchedResidenceIds;
        }

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
