package di.uoa.roomexplorer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Renter extends User {

    @OneToMany(mappedBy = "renter")
    Set<Reservation> reservations;

    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
