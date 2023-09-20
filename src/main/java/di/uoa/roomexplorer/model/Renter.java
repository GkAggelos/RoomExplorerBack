package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Renter extends User {

    public Renter(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "renter", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Reservation> reservations;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Message> messages;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
