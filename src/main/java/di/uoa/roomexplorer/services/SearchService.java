package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Search;
import di.uoa.roomexplorer.repositories.ReservationRepo;
import di.uoa.roomexplorer.repositories.SearchRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final SearchRepo searchRepo;
    private final ReservationRepo reservationRepo;
    private final MatrixFactorizationService matrixFactorizationService;

    public SearchService(SearchRepo searchRepo, ReservationRepo reservationRepo, MatrixFactorizationService matrixFactorizationService) {
        this.searchRepo = searchRepo;
        this.reservationRepo = reservationRepo;
        this.matrixFactorizationService = matrixFactorizationService;
    }

    public Search addSearch(Search newSearch) {
        Optional<List<Reservation>> reservations = this.reservationRepo.findReservationsByRenter_Id(newSearch.getRenter().getId());
        // if the renter has written a review for this residence, there is no need to add it to its search history
        if (reservations.isPresent()) {
            for (Reservation reservation : reservations.get()) {
                if (reservation.getResidence().getId().equals(newSearch.getResidence().getId()) && !reservation.getReview().isEmpty()) {
                    return null;
                }
            }
        }

        this.matrixFactorizationService.updateCellId(newSearch.getRenter().getId(), newSearch.getResidence().getId(),
                (int) this.matrixFactorizationService.getCellValueId(newSearch.getRenter().getId(), newSearch.getResidence().getId())+3);
        this.matrixFactorizationService.train();

        return searchRepo.save(newSearch);
    }

    public void deleteSearchesByRenterId(Long renterId) {
        searchRepo.deleteSearchesByRenter_Id(renterId);
    }

}
