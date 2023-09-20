package di.uoa.roomexplorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Search {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Renter renter;

    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = false)
    Residence residence;

    public Search() {}
}
