package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.repositories.HostRepo;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class HostService {
    private final HostRepo hostRepo;

    public HostService(HostRepo hostRepo) {
        this.hostRepo = hostRepo;
    }
    public Host addHost(Host newHost) {
        return hostRepo.save(newHost);
    }
    public List<Host> findAllHosts() {
        return hostRepo.findAll();
    }

    public Host findByUsername(String username) { return hostRepo.findHostByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User by username" + username + " was not found"));
    }
    public Host findHostById(Long id) {
        return hostRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }
    public Host updateHost(Host newHost) {
        return hostRepo.save(newHost);
    }
    public void deleteHost(Long id) {
        hostRepo.deleteById(id);
    }
}
