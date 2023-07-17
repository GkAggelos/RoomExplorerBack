package di.uoa.roomexplorer.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Residence {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    Double pricing;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    Integer area;

    @Column(nullable = false)
    Integer floor;

    @Column(nullable = false)
    Integer peopleCapacity;

    @Column(nullable = false)
    RoomType roomType;

    @Column(columnDefinition="MEDIUMTEXT")
    String comment;

    String photo;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    Host host;

    @Column(nullable = false)
    Boolean has_wifi;

    @Column(nullable = false)
    Boolean has_heating;

    @Column(nullable = false)
    Boolean has_air_condition;

    @Column(nullable = false)
    Boolean has_cuisine;

    @Column(nullable = false)
    Boolean has_tv;

    @Column(nullable = false)
    Boolean has_parking;

    @Column(nullable = false)
    Boolean has_elevator;

    @OneToMany(mappedBy = "residence")
    Set<Reservation> reservations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPricing() {
        return pricing;
    }

    public void setPricing(Double pricing) {
        this.pricing = pricing;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(Integer peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Boolean getHas_wifi() {
        return has_wifi;
    }

    public void setHas_wifi(Boolean has_wifi) {
        this.has_wifi = has_wifi;
    }

    public Boolean getHas_heating() {
        return has_heating;
    }

    public void setHas_heating(Boolean has_heating) {
        this.has_heating = has_heating;
    }

    public Boolean getHas_air_condition() {
        return has_air_condition;
    }

    public void setHas_air_condition(Boolean has_air_condition) {
        this.has_air_condition = has_air_condition;
    }

    public Boolean getHas_cuisine() {
        return has_cuisine;
    }

    public void setHas_cuisine(Boolean has_cuisine) {
        this.has_cuisine = has_cuisine;
    }

    public Boolean getHas_tv() {
        return has_tv;
    }

    public void setHas_tv(Boolean has_tv) {
        this.has_tv = has_tv;
    }

    public Boolean getHas_parking() {
        return has_parking;
    }

    public void setHas_parking(Boolean has_parking) {
        this.has_parking = has_parking;
    }

    public Boolean getHas_elevator() {
        return has_elevator;
    }

    public void setHas_elevator(Boolean has_elevator) {
        this.has_elevator = has_elevator;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
