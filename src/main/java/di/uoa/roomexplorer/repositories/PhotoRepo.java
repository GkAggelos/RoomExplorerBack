package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo, Long> {
}
