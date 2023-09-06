package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.model.Residence;

import java.time.LocalDate;
import java.util.List;

public interface CustomResidenceRepo {

    PageResponse<List<Residence>> findResidencesBySearchAndFilter(String location, LocalDate arrivalDate, LocalDate leaveDate,
                                                                  Integer peopleCapacity, String roomType, Boolean parking,
                                                                  Boolean livingRoom, Boolean wifi, Boolean heating,
                                                                  Boolean airCondition, Boolean cuisine, Boolean tv,
                                                                  Boolean elevator, String price ,int pageNumber);

}
