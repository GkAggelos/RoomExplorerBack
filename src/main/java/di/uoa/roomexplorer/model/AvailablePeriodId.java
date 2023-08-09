package di.uoa.roomexplorer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class AvailablePeriodId implements Serializable {

    public Residence residence;
    public LocalDate date;

    public AvailablePeriodId() {}

    public AvailablePeriodId(Residence residence, LocalDate date) {
        this.residence = residence;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailablePeriodId that = (AvailablePeriodId) o;
        return Objects.equals(residence, that.residence) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(residence, date);
    }
}
