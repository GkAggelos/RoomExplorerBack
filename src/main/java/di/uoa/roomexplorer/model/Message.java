package di.uoa.roomexplorer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = false)
    Residence residence;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Renter renter;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    String sender;

    @Column(nullable = false)
    Date date;

}
