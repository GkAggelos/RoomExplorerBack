package di.uoa.roomexplorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Reservation {

    public Reservation(LocalDate reservationDate, LocalDate arrivalDate, LocalDate leaveDate,
                       ReservationState state, Residence residence, Renter renter, Integer stars, String review) {
        this.reservationDate = reservationDate;
        this.arrivalDate = arrivalDate;
        this.leaveDate = leaveDate;
        this.state = state;
        this.residence = residence;
        this.renter = renter;
        this.stars = stars;
        this.review = review;
    }

    public Reservation() {}

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Integer stars;

    @Column(columnDefinition="LONGTEXT")
    String review;

    @Column(name = "reservation_date", columnDefinition = "DATE")
    LocalDate reservationDate;

    @Column(name = "arrival_date", columnDefinition = "DATE")
    LocalDate arrivalDate;

    @Column(name = "leave_date", columnDefinition = "DATE")
    LocalDate leaveDate;

    @Column(nullable = false)
    ReservationState state;

    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = false)
    Residence residence;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Renter renter;
}
