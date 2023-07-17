package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.services.HostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public ResponseEntity<List<Host>> getAllHosts() {
        List<Host> hosts = hostService.findAllHosts();
        return new ResponseEntity<>(hosts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Host> getHostById(@PathVariable("id") Long id) {
        Host host = hostService.findHostById(id);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Host> addHost(@RequestBody Host newhost) {
        Host host = hostService.addHost(newhost);
        return new ResponseEntity<>(host, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Host> updateHost(@RequestBody Host newhost) {
        Host host = hostService.updateHost(newhost);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHost(@PathVariable("id") Long id) {
        hostService.deleteHost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
