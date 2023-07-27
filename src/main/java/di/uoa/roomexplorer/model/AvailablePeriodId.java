package di.uoa.roomexplorer.model;

import java.io.Serializable;
import java.time.LocalDate;


public class AvailablePeriodId implements Serializable {

    public Residence residence;
    public LocalDate date;

    public AvailablePeriodId() {}

    public AvailablePeriodId(Residence residence, LocalDate date) {
        this.residence = residence;
        this.date = date;
    }
}
