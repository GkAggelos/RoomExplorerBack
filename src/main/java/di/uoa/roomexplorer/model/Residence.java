package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class Residence {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    Double pricing;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String area;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    Double coordinateX;

    @Column(nullable = false)
    Double coordinateY;
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

    @OneToMany(mappedBy = "residence", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Message> messages;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    Host host;

    @Column(nullable = false)
    LocalDate available_from;

    @Column(nullable = false)
    LocalDate available_till;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_living_room;
    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_wifi;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_heating;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_air_condition;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_cuisine;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_tv;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_parking;

    @Column(nullable = false, columnDefinition="BOOL")
    Boolean has_elevator;

    @OneToMany(mappedBy = "residence", cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Reservation> reservations;

    public Residence(Double pricing, String city, String area, String address, Double coordinateX, Double coordinateY, Integer floor, Integer peopleCapacity,
                     Integer bedNumber, Integer bathroomNumber, Integer bedroomNumber, Integer acreage,
                     RoomType roomType, Integer reviewsNumber, Integer starsAverage, Host host, LocalDate available_from, LocalDate available_till,
                     Boolean has_living_room, Boolean has_wifi, Boolean has_heating, Boolean has_air_condition,
                     Boolean has_cuisine, Boolean has_tv, Boolean has_parking, Boolean has_elevator) {

        this.pricing = pricing;
        this.city = city;
        this.area = area;
        this.address = address;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
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
}
