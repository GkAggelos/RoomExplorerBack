package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepo extends JpaRepository<Search, Long> {

    List<Search> findSearchesByRenter_Id(Long renter_id);
    void deleteSearchesByRenter_Id(Long renter_id);

    @Query("SELECT s.residence.id FROM Search s WHERE s.renter.id = ?1")
    List<Long> findSearchedResidenceIds(Long renter_id);
}
