package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Message;
import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.services.MessageService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("find/{id}")
    @RolesAllowed({"host", "renter"})
    public  ResponseEntity<Message> getMessageById(@PathVariable("id") Long id) {
        Message message = messageService.findMessageById(id);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/find/residence/{residence_id}/{page}")
    @RolesAllowed({"host"})
    public PageResponse<Page<Message>> getMessagesByResidenceIdPagination(@PathVariable("residence_id") Long residence_id, @PathVariable("page") int page) {
        Page<Message> messages = messageService.findMessagesByResidence_idPagination(residence_id, page);
        return new PageResponse<>(messages.getTotalElements(), messages);
    }

    @GetMapping("/find/renter/{page}")
    @RolesAllowed({"renter"})
    public PageResponse<Page<Message>> getMessagesByRenterIdPagination(@RequestParam Long id, @PathVariable("page") int page) {
        Page<Message> messages = messageService.findMessagesByRenter_idPagination(id, page);
        return new PageResponse<>(messages.getTotalElements(), messages);
    }

    @PostMapping("/add")
    @RolesAllowed({"host", "renter"})
    public ResponseEntity<Message> addMessage(@RequestBody Message newMessage) {
        Message message = messageService.addMessage(newMessage);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"host", "renter"})
    public ResponseEntity<?> deleteMessage(@PathVariable("id") Long id) {
        messageService.DeleteMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
