package di.uoa.roomexplorer.model;

import jakarta.persistence.*;

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

    public Search(Long id, Renter renter, Residence residence) {
        this.id = id;
        this.renter = renter;
        this.residence = residence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }
}
