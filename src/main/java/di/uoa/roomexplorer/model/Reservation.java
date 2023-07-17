package di.uoa.roomexplorer.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
     Long id;

    Integer stars;

    @Column(columnDefinition="LONGTEXT")
    String review;

    @Column(name = "reservation_date", columnDefinition = "DATE")
    Date reservationDate;

    @Column(name = "arrival_date", columnDefinition = "DATE")
    Date arrivalDate;

    @Column(name = "leave_date", columnDefinition = "DATE")
    Date leaveDate;

    @Column(nullable = false)
    ReservationState state;

    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = false)
    Residence residence;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Renter renter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }
}
