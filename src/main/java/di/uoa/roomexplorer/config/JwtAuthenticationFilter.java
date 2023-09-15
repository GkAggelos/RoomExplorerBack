package di.uoa.roomexplorer.config;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.User;
import di.uoa.roomexplorer.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;
            final String role;
            final String id;
            final Long requestId;
            final Long userId;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            role = jwtService.extractRole(jwt);

            if ((id = request.getParameter("id")) != null) {
                requestId = Long.parseLong(id);
                userId = jwtService.extractId(jwt);
                if (!requestId.equals(userId) && !role.equals("admin")) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = this.userService.findByUsername(username, role).orElseThrow(() -> new UserNotFoundException("User with name" + username + "was not found"));
                if (jwtService.isTokenValid(jwt, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "(Origin, Access-Control-Allow-Origin, Content-Type, Accept, Authorization, X-Requested-With, " +
                        "Access-Control-Request-Method, Access-Control-Request-Headers");
            filterChain.doFilter(request, response);
    }
}
