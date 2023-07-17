package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

@Entity
public class Host extends User {

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
