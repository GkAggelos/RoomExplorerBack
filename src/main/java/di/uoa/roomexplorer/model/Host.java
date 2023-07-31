package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
    @OneToMany(mappedBy = "host")
    @JsonIgnore
    Set<Residence> residences;

    @Column(columnDefinition="BOOL")
    @Value("${props.boolean.approved:#{false}}")
    Boolean approved = false;

    public void setResidences(Set<Residence> residences) {
        this.residences = residences;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Set<Residence> getResidences() {
        return residences;
    }

    public Boolean getApproved() {
        return approved;
    }
}
