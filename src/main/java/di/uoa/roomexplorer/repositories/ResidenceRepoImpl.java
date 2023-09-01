package di.uoa.roomexplorer.repositories;

import di.uoa.roomexplorer.model.PageResponse;
import di.uoa.roomexplorer.model.Reservation;
import di.uoa.roomexplorer.model.ReservationState;
import di.uoa.roomexplorer.model.Residence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ResidenceRepoImpl implements CustomResidenceRepo {

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public PageResponse<List<Residence>> findResidencesBySearch(String city, LocalDate arrivalDate, LocalDate leaveDate, Integer peopleCapacity, int pageNumber) {
        Query query = entityManager.createQuery(
                "SELECT r FROM Residence r WHERE (LOCATE(LOWER(r.city), LOWER(?1)) > 0) AND " +
                "r.available_from <= ?2 AND r.available_till >= ?3 AND " +
                "r.peopleCapacity>=?4 order by r.pricing");

        Query countQuery = entityManager.createQuery("SELECT COUNT(r) FROM Residence r WHERE (LOCATE(LOWER(r.city), LOWER(?1)) > 0) AND " +
                "r.available_from <= ?2 AND r.available_till >= ?3 AND " +
                "r.peopleCapacity>=?4");

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

        List<Residence> possibleResidences = query.getResultList();
        List<Residence> residences = new ArrayList<>(possibleResidences);

        for (Residence residence : possibleResidences) {
            Set<Reservation> reservations = residence.getReservations();
            for (Reservation reservation : reservations) {
                if (arrivalDate.isBefore(reservation.getLeaveDate()) && reservation.getArrivalDate().isBefore(leaveDate) && !reservation.getState().equals(ReservationState.REJECTED)) {
                    residences.remove(residence);
                    break;
                }
            }
        }

        PageResponse<List<Residence>> pageResponse = new PageResponse<>(recordCount, residences);

        return pageResponse;
    }
}
