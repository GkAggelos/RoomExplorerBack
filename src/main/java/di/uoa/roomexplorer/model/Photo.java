package di.uoa.roomexplorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, columnDefinition= "LONGBLOB")
    String url;

    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = false)
    Residence residence;
}
