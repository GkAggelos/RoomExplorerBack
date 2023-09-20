package di.uoa.roomexplorer.auth;

import di.uoa.roomexplorer.config.JwtService;
import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Host;
import di.uoa.roomexplorer.model.Renter;
import di.uoa.roomexplorer.model.User;
import di.uoa.roomexplorer.services.HostService;
import di.uoa.roomexplorer.services.RenterService;
import di.uoa.roomexplorer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final HostService hostService;
    private final RenterService renterService;
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
            hostService.addHost(host);
            renter = renterService.addRenter(renter);
            jwtToken = jwtService.generateToken(renter.getUsername(), renter.getId(), "renter");
        }
        if (request.getRole().equals("host")) {
            host = new Host(request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber());
            host = hostService.addHost(host);
            jwtToken = jwtService.generateToken(host.getUsername(), host.getId(), request.getRole());
        }
        if (request.getRole().equals("renter")) {
            renter = new Renter(request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber());
            renter = renterService.addRenter(renter);
            jwtToken = jwtService.generateToken(renter.getUsername(), renter.getId(), request.getRole());
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String jwtToken = "";
        try {
            User user = userService.findByUsername(request.getUsername(),request.getRole()).orElseThrow(() -> new UserNotFoundException("User with name" + request.getUsername() + "was not found"));
            if (request.getRole().equals("host")) {
                if (!hostService.isHostApproved(request.getUsername())) {
                    return AuthenticationResponse.builder()
                            .token(jwtToken)
                            .message("Host not approved")
                            .build();
                }
            }
            try {
                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
                }
                jwtToken = jwtService.generateToken(request.getUsername(), user.getId(), request.getRole());
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .message("")
                        .build();
            }
            catch (ResponseStatusException ex) {
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .message("User password incorrect")
                        .build();
            }
        }
        catch (UserNotFoundException ex) {
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("User with this username not found")
                    .build();
        }
    }
}
