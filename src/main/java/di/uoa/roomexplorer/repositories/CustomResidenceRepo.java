package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.Residence;

import java.time.LocalDate;
import java.util.List;

public interface CustomResidenceRepo {

    List<Residence> findResidencesBySearch(String location, LocalDate arrivalDate, LocalDate leaveDate, Integer peopleCapacity);
}
