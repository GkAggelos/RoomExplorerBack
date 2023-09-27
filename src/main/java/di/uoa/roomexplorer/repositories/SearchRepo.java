package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepo extends JpaRepository<Search, Long> {

    void deleteSearchesByRenter_Id(Long renter_id);
}
