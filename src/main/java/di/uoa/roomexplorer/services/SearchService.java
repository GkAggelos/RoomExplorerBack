package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.model.Search;
import di.uoa.roomexplorer.repositories.ReservationRepo;
import di.uoa.roomexplorer.repositories.SearchRepo;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final SearchRepo searchRepo;
    private final ReservationRepo reservationRepo;

    public SearchService(SearchRepo searchRepo, ReservationRepo reservationRepo) {
        this.searchRepo = searchRepo;
        this.reservationRepo = reservationRepo;
    }

    public Search addSearch(Search newSearch) {
        Optional<List<Reservation>> reservations = this.reservationRepo.findReservationsByRenter_Id(newSearch.getRenter().getId());
        if (reservations.isPresent()) {
            for (Reservation reservation : reservations.get()) {
                if (!reservation.getReview().isEmpty() && reservation.getReview() != null) {
                    return null;
                }
            }
        }
        return searchRepo.save(newSearch);
    }

    public void deleteSearch(Long id) {
        searchRepo.deleteById(id);
    }

    public List<Search> findSearchByRenterId(Long renterId) {
        return searchRepo.findSearchesByRenter_Id(renterId);
    }

    public void deleteSearchesByRenterId(Long renterId) {
        searchRepo.deleteSearchesByRenter_Id(renterId);
    }

    public List<Residence> findResidenceByRenterId(Long renterId) {
        List<Search> searches = this.findSearchByRenterId(renterId);
        List<Residence> residences = new LinkedList<>();

        for (Search search : searches) {
            residences.add(search.getResidence());
        }

        return residences;
    }

    public List<Long> findResidenceIdsByRenterId(Long renterId) {
        return searchRepo.findSearchedResidenceIds(renterId);
    }
}
