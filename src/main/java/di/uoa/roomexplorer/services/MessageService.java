package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.MessageNotFoundException;
import di.uoa.roomexplorer.model.Message;
import di.uoa.roomexplorer.repositories.MessageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Message addMessage(Message new_message) {
        return messageRepo.save(new_message);
    }

    public Page<Message> findMessagesByResidence_idPagination(Long residence_id, int page) {
        return messageRepo.findMessagesByResidence_IdOrderByDateDesc(residence_id, PageRequest.of(page, 10))
                .orElseThrow(() -> new MessageNotFoundException("Messages for residence with id" + residence_id + "was not found"));
    }

    public Page<Message> findMessagesByRenter_idPagination(Long renter_id, int page) {
        return messageRepo.findMessagesByRenter_IdOrderByDateDesc(renter_id, PageRequest.of(page, 10))
                .orElseThrow(() -> new MessageNotFoundException("Messages for renter with id" + renter_id + "was not found"));
    }

    public Message findMessageById(Long id) {
        return messageRepo.findById(id).orElseThrow(() -> new MessageNotFoundException("Message with id" + id + "was not found"));
    }

    public void DeleteMessage(Long id) {
        messageRepo.deleteById(id);
    }
}
