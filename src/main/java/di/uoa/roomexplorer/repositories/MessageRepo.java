package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {
    Optional<Page<Message>> findMessagesByResidence_IdOrderByDateDesc(Long residence_id, Pageable pageable);

    Optional<Page<Message>> findMessagesByRenter_IdOrderByDateDesc(Long renter_id, Pageable pageable);
}
