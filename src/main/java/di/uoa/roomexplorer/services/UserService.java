package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.auth.AuthenticationResponse;
import di.uoa.roomexplorer.config.JwtService;
import di.uoa.roomexplorer.model.Admin;
import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.model.User;
import di.uoa.roomexplorer.repositories.AdminRepo;
import di.uoa.roomexplorer.repositories.HostRepo;
import di.uoa.roomexplorer.repositories.RenterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RenterRepo renterRepo;
    private final HostRepo hostRepo;
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Optional<User> findByUsername(String username, String role) {
        User user = new User();
        Host host;
        Renter renter;
        Admin admin;
        if (role.equals("host")) {
            host = hostRepo.findHostByUsername(username).orElseThrow();
            user = new User(host.getId(), host.getUsername(),host.getPassword(), host.getFirstName(), host.getLastName(), host.getEmail(), host.getPhoneNumber(), host.getPhoto());
        }
        if (role.equals("renter")) {
            renter = renterRepo.findRenterByUsername(username).orElseThrow();
            user = new User(renter.getId(), renter.getUsername(),renter.getPassword(), renter.getFirstName(), renter.getLastName(), renter.getEmail(), renter.getPhoneNumber(), renter.getPhoto());
        }
        if (role.equals("admin")) {
            admin = adminRepo.findAdminByUsername(username).orElseThrow();
            user = new User(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getFirstName(), admin.getLastName(), admin.getEmail(), admin.getPhoneNumber(), admin.getPhoto());
        }
        return Optional.of(user);
    }

    public AuthenticationResponse updateUser(String username, String role, User user) {
        Host host;
        Renter renter;
        String jwtToken = "";

        if (role.equals("host")) {
            host = hostRepo.findHostByUsername(username).orElseThrow();
            host.setUsername(user.getUsername());
            host.setPassword(passwordEncoder.encode(user.getPassword()));
            host.setFirstName(user.getFirstName());
            host.setLastName(user.getLastName());
            host.setEmail(user.getEmail());
            host.setPhoneNumber(user.getPhoneNumber());
            host.setPhoto(user.getPhoto());
            hostRepo.save(host);
            try {
                renter = renterRepo.findRenterByUsername(username).orElseThrow();
                renter.setUsername(user.getUsername());
                renter.setPassword(passwordEncoder.encode(user.getPassword()));
                renter.setFirstName(user.getFirstName());
                renter.setLastName(user.getLastName());
                renter.setEmail(user.getEmail());
                renter.setPhoneNumber(user.getPhoneNumber());
                renter.setPhoto(user.getPhoto());
                renterRepo.save(renter);
                jwtToken = jwtService.generateToken(host.getUsername(), host.getId(), role);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
            catch (Exception ex) {
                jwtToken = jwtService.generateToken(host.getUsername(), host.getId(), role);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
        }
        if (role.equals("renter")) {
            renter = renterRepo.findRenterByUsername(username).orElseThrow();
            renter.setUsername(user.getUsername());
            renter.setPassword(passwordEncoder.encode(user.getPassword()));
            renter.setFirstName(user.getFirstName());
            renter.setLastName(user.getLastName());
            renter.setEmail(user.getEmail());
            renter.setPhoneNumber(user.getPhoneNumber());
            renter.setPhoto(user.getPhoto());
            renterRepo.save(renter);
            try {
                host = hostRepo.findHostByUsername(username).orElseThrow();
                host.setUsername(user.getUsername());
                host.setPassword(passwordEncoder.encode(user.getPassword()));
                host.setFirstName(user.getFirstName());
                host.setLastName(user.getLastName());
                host.setEmail(user.getEmail());
                host.setPhoneNumber(user.getPhoneNumber());
                host.setPhoto(user.getPhoto());
                hostRepo.save(host);
                jwtToken = jwtService.generateToken(renter.getUsername(), renter.getId(), role);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
            catch (Exception ex) {
                jwtToken = jwtService.generateToken(renter.getUsername(), renter.getId(), role);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
