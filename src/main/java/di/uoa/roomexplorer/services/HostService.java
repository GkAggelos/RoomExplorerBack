package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.repositories.HostRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Page<Host> findAllHostsPagination(int page) {
        return hostRepo.findAll(PageRequest.of(page, 10));
    }

    public List<String> findAllUsernames() {
        List<Host> hosts = findAllHosts();
        List<String> usernames = new ArrayList<>();
        for (Host host : hosts) {
            usernames.add(host.getUsername());
        }
        return usernames;
    }

    public List<String> findAllEmails() {
        List<Host> hosts = findAllHosts();
        List<String> emails  = new ArrayList<>();
        for (Host host : hosts) {
            emails.add(host.getEmail());
        }
        return emails;
    }

    public Host findByUsername(String username) { return hostRepo.findHostByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User by username" + username + " was not found"));
    }
    public Host findHostById(Long id) {
        return hostRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public boolean isHostApproved(String username) {
        Host host = findByUsername(username);
        return host.getApproved();
    }

    public Host updateHost(Host newHost) {
        return hostRepo.save(newHost);
    }
}
