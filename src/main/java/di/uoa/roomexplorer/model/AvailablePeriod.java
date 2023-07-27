package di.uoa.roomexplorer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(AvailablePeriodId.class)
public class AvailablePeriod {

    @Id
    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = false)
    Residence residence;

    @Id
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate date;

    @Column(nullable = false)
    Integer duration;

    public AvailablePeriod() {}

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
