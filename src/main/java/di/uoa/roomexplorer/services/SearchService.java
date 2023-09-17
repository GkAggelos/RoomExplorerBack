package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.model.Residence;
import di.uoa.roomexplorer.model.Search;
import di.uoa.roomexplorer.repositories.SearchRepo;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SearchService {

    private final SearchRepo searchRepo;

    public SearchService(SearchRepo searchRepo) {
        this.searchRepo = searchRepo;
    }

    public Search addSearch(Search newSearch) {
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
