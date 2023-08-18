package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
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
    Integer bedNumber;

    @Column(nullable = false)
    Integer bathroomNumber;

    @Column(nullable = false)
    Integer bedroomNumber;

    @Column(nullable = false)
    Integer acreage;

    @Column(nullable = false)
    RoomType roomType;

    @Column(nullable = false)
    Integer reviewsNumber;

    @Column(nullable = false)
    Integer starsAverage;

    @Column(columnDefinition="MEDIUMTEXT")
    String comment;

    @Column(columnDefinition="MEDIUMTEXT")
    String description;

    @OneToMany(mappedBy = "residence", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Photo> photos;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    Host host;

    @Column(nullable = false)
    LocalDate available_from;

    @Column(nullable = false)
    LocalDate available_till;

    @Column(nullable = false)
    Boolean has_living_room;
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

    @OneToMany(mappedBy = "residence", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Reservation> reservations;

    public Residence(Double pricing, String location, Integer area, Integer floor, Integer peopleCapacity,
                     Integer bedNumber, Integer bathroomNumber, Integer bedroomNumber, Integer acreage,
                     RoomType roomType, Integer reviewsNumber, Integer starsAverage, Host host, LocalDate available_from, LocalDate available_till,
                     Boolean has_living_room, Boolean has_wifi, Boolean has_heating, Boolean has_air_condition,
                     Boolean has_cuisine, Boolean has_tv, Boolean has_parking, Boolean has_elevator) {

        this.pricing = pricing;
        this.location = location;
        this.area = area;
        this.floor = floor;
        this.peopleCapacity = peopleCapacity;
        this.bedNumber = bedNumber;
        this.bathroomNumber = bathroomNumber;
        this.bedroomNumber = bedroomNumber;
        this.acreage = acreage;
        this.roomType = roomType;
        this.reviewsNumber = reviewsNumber;
        this.starsAverage = starsAverage;
        this.host = host;
        this.available_from = available_from;
        this.available_till = available_till;
        this.has_living_room = has_living_room;
        this.has_wifi = has_wifi;
        this.has_heating = has_heating;
        this.has_air_condition = has_air_condition;
        this.has_cuisine = has_cuisine;
        this.has_tv = has_tv;
        this.has_parking = has_parking;
        this.has_elevator = has_elevator;
    }

    public Residence() {}

    public LocalDate getAvailable_from() {
        return available_from;
    }

    public void setAvailable_from(LocalDate available_from) {
        this.available_from = available_from;
    }

    public LocalDate getAvailable_till() {
        return available_till;
    }

    public void setAvailable_till(LocalDate available_till) {
        this.available_till = available_till;
    }
//    @OneToMany(mappedBy = "residence")
//    @JsonIgnore
//    Set<AvailablePeriod> availablePeriods;

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

    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(Integer bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Integer getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(Integer bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public Integer getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(Integer bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public Integer getAcreage() { return acreage; }

    public void setAcreage(Integer acreage) { this.acreage = acreage; }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getReviewsNumber() {
        return reviewsNumber;
    }

    public void setReviewsNumber(Integer reviewsNumber) {
        this.reviewsNumber = reviewsNumber;
    }

    public Integer getStarsAverage() {
        return starsAverage;
    }

    public void setStarsAverage(Integer starsAverage) {
        this.starsAverage = starsAverage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Boolean getHas_living_room() {
        return has_living_room;
    }

    public void setHas_living_room(Boolean has_living_room) {
        this.has_living_room = has_living_room;
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

//    public Set<AvailablePeriod> getAvailablePeriods() {
//        return availablePeriods;
//    }
//
//    public void setAvailablePeriods(Set<AvailablePeriod> availablePeriods) {
//        this.availablePeriods = availablePeriods;
//    }

}
