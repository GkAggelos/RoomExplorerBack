package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.services.HostService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/host")
public class HostController {

    private final HostService hostService;

    @Autowired
    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @GetMapping("/all/{page}")
    @RolesAllowed({"admin"})
    public PageResponse<Page<Host>> getAllHosts(@PathVariable("page") int page) {
        Page<Host> hosts = hostService.findAllHostsPagination(page);
        return new PageResponse<>(hosts.getTotalElements(), hosts);
    }

    @GetMapping("/find")
    @RolesAllowed({"admin", "host"})
    public ResponseEntity<Host> getHostById(@RequestParam Long id) {
        Host host = hostService.findHostById(id);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @GetMapping("/find/all/usernames")
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = hostService.findAllUsernames();
        return new ResponseEntity<>(usernames,HttpStatus.OK);
    }

    @GetMapping("/find/all/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = hostService.findAllEmails();
        return new ResponseEntity<>(emails,HttpStatus.OK);
    }

    @PutMapping("/update")
    @RolesAllowed({"admin", "host"})
    public ResponseEntity<Host> updateHost(@RequestBody Host newhost) {
        Host host = hostService.updateHost(newhost);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }
}
