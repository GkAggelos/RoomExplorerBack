package di.uoa.roomexplorer.auth;

import di.uoa.roomexplorer.config.JwtService;
import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.model.User;
import di.uoa.roomexplorer.repositories.AdminRepo;
import di.uoa.roomexplorer.repositories.HostRepo;
import di.uoa.roomexplorer.repositories.RenterRepo;
import di.uoa.roomexplorer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final HostRepo hostRepo;
    private final RenterRepo renterRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        Host host;
        Renter renter;
        String jwtToken = "";
        if (request.getRole().equals("host-renter")) {
            host = new Host(request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber());
            renter = new Renter(request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber());
            host = hostRepo.save(host);
            renter = renterRepo.save(renter);
            jwtToken = jwtService.generateToken(renter.getUsername(), renter.getId(), "renter");
        }
        if (request.getRole().equals("host")) {
            host = new Host(request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber());
            host = hostRepo.save(host);
            jwtToken = jwtService.generateToken(host.getUsername(), host.getId(), request.getRole());
        }
        if (request.getRole().equals("renter")) {
            renter = new Renter(request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber());
            renter = renterRepo.save(renter);
            jwtToken = jwtService.generateToken(renter.getUsername(), renter.getId(), request.getRole());
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String jwtToken = "";
        User user = userService.findByUsername(request.getUsername(),request.getRole()).orElseThrow();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        jwtToken = jwtService.generateToken(request.getUsername(), user.getId(), request.getRole());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
