package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Host extends User {

    public Host(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    @OneToMany(mappedBy = "host", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Residence> residences;

    @Column(columnDefinition="BOOL")
    @Value("${props.boolean.approved:#{false}}")
    Boolean approved = false;

    public void setResidences(Set<Residence> residences) {
        this.residences = residences;
    }

    public Set<Residence> getResidences() {
        return residences;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getApproved() {
        return approved;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
