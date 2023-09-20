package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.model.Residence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ResidenceRepoImpl implements CustomResidenceRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PageResponse<List<Residence>> findResidencesBySearchAndFilter(String city, LocalDate arrivalDate, LocalDate leaveDate,
                                                                         Integer peopleCapacity, String roomType, Boolean parking,
                                                                         Boolean livingRoom, Boolean wifi, Boolean heating,
                                                                         Boolean airCondition, Boolean cuisine, Boolean tv,
                                                                         Boolean elevator, String price ,int pageNumber) {
        boolean flag = false;
        String stringQuery = "SELECT r FROM Residence r WHERE (LOCATE(LOWER(r.city), LOWER(?1)) > 0) AND " +
                "r.available_from <= ?2 AND r.available_till >= ?3 AND " +
                "r.peopleCapacity>=?4 ";
        String stringCountQuery = "SELECT COUNT(r) FROM Residence r WHERE (LOCATE(LOWER(r.city), LOWER(?1)) > 0) AND " +
                "r.available_from <= ?2 AND r.available_till >= ?3 AND " +
                "r.peopleCapacity>=?4 ";

        if (!price.isEmpty()) {
            if (price.equals("priceLess50")) {
                stringQuery = stringQuery.concat("AND r.pricing <= 50.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing <= 50.0 ");
            }
            if (price.equals("price50_100")) {
                stringQuery = stringQuery.concat("AND r.pricing > 50.0 AND r.pricing <= 100.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing > 50.0 AND r.pricing <= 100.0 ");
            }
            if (price.equals("price100_200")) {
                stringQuery = stringQuery.concat("AND r.pricing > 100.0 AND r.pricing <= 200.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing > 100.0 AND r.pricing <= 200.0 ");
            }
            if (price.equals("price200_350")) {
                stringQuery = stringQuery.concat("AND r.pricing > 200.0 AND r.pricing <= 350.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing > 200.0 AND r.pricing <= 350.0 ");
            }
            if (price.equals("price350_600")) {
                stringQuery = stringQuery.concat("AND r.pricing > 350.0 AND r.pricing <= 600.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing > 350.0 AND r.pricing <= 600.0 ");
            }
            if (price.equals("price600_1000")) {
                stringQuery = stringQuery.concat("AND r.pricing > 600.0 AND r.pricing <= 1000.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing > 600.0 AND r.pricing <= 1000.0 ");
            }
            if (price.equals("priceAbove1000")) {
                stringQuery = stringQuery.concat("AND r.pricing > 1000.0 ");
                stringCountQuery = stringCountQuery.concat("AND r.pricing > 1000.0 ");
            }
        }

        if (!roomType.isEmpty()) {
            stringQuery = stringQuery.concat("AND ( ");
            stringCountQuery = stringCountQuery.concat("AND ( ");
            if (roomType.indexOf('p') != -1) {
                stringQuery = stringQuery.concat("r.roomType = 0 ");
                stringCountQuery = stringCountQuery.concat("r.roomType = 0 ");
                flag = true;
            }
            if (roomType.indexOf('s') != -1) {
                if (flag) {
                    stringQuery = stringQuery.concat("OR r.roomType = 1 ");
                    stringCountQuery = stringCountQuery.concat("OR r.roomType = 1 ");
                }
                else {
                    stringQuery = stringQuery.concat("r.roomType = 1 ");
                    stringCountQuery = stringCountQuery.concat("r.roomType = 1 ");
                    flag = true;
                }
            }
            if (roomType.indexOf('w') != -1) {
                if (flag) {
                    stringQuery = stringQuery.concat("OR r.roomType = 2 ");
                    stringCountQuery = stringCountQuery.concat("OR r.roomType = 2 ");
                }
                else {
                    stringQuery = stringQuery.concat("r.roomType = 2 ");
                    stringCountQuery = stringCountQuery.concat("r.roomType = 2 ");
                }
            }
            stringQuery = stringQuery.concat(") ");
            stringCountQuery = stringCountQuery.concat(") ");
        }
        if (parking) {
            stringQuery = stringQuery.concat("AND r.has_parking = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_parking = true ");
        }
        if (livingRoom) {
            stringQuery = stringQuery.concat("AND r.has_living_room = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_living_room = true ");
        }
        if (wifi) {
            stringQuery = stringQuery.concat("AND r.has_wifi = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_wifi = true ");
        }
        if (heating) {
            stringQuery = stringQuery.concat("AND r.has_heating = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_heating = true ");
        }
        if (airCondition) {
            stringQuery = stringQuery.concat("AND r.has_air_condition = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_air_condition = true ");
        }
        if (cuisine) {
            stringQuery = stringQuery.concat("AND r.has_cuisine = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_cuisine = true ");
        }
        if (tv) {
            stringQuery = stringQuery.concat("AND r.has_tv = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_tv = true ");
        }
        if (elevator) {
            stringQuery = stringQuery.concat("AND r.has_elevator = true ");
            stringCountQuery = stringCountQuery.concat("AND r.has_elevator = true ");
        }
        stringQuery = stringQuery.concat("AND (SELECT COUNT(*) FROM Reservation reservation WHERE reservation.residence = r AND " +
                                "(?3 <= reservation.arrivalDate OR  ?2 >= reservation.leaveDate)) = " +
                                "(SELECT COUNT(*) FROM Reservation reservation WHERE reservation.residence = r) "+
                                "ORDER BY r.pricing");
        stringCountQuery = stringCountQuery.concat("AND (SELECT COUNT(*) FROM Reservation reservation WHERE reservation.residence = r AND " +
                "(?3 <= reservation.arrivalDate OR  ?2 >= reservation.leaveDate)) = " +
                "(SELECT COUNT(*) FROM Reservation reservation WHERE reservation.residence = r) "+
                "ORDER BY r.pricing");

        Query query = entityManager.createQuery(stringQuery);

        Query countQuery = entityManager.createQuery(stringCountQuery);

        countQuery.setParameter(1, city);
        countQuery.setParameter(2, arrivalDate);
        countQuery.setParameter(3, leaveDate);
        countQuery.setParameter(4, peopleCapacity);

        query.setParameter(1, city);
        query.setParameter(2, arrivalDate);
        query.setParameter(3, leaveDate);
        query.setParameter(4, peopleCapacity);

        long recordCount = (long) countQuery.getResultList().get(0);

        query.setFirstResult(pageNumber * 10);
        query.setMaxResults(10);

        List<Residence> residences = query.getResultList();

        return new PageResponse<>(recordCount, residences);
    }
}
