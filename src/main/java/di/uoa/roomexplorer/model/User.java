package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@Builder
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(unique = true)
    String username;
    String password;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    String phoneNumber;
    @Column(columnDefinition= "LONGBLOB")
    String photo;
    @JsonIgnore
    String role;

    public User() {
    }

    public User(Long id, String username, String password, String firstName, String lastName, String email, String phoneNumber, String photo,
                 String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!role.isEmpty()) authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }
}
