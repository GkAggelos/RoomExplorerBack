package di.uoa.roomexplorer.repositories;

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
    public List<Residence> findResidencesBySearch(String city, LocalDate arrivalDate, LocalDate leaveDate, Integer peopleCapacity) {
        Query query = entityManager.createQuery(
                "SELECT r FROM Residence r WHERE (LOCATE(LOWER(r.city), LOWER(?1)) > 0) AND " +
                "r.available_from <= ?2 AND r.available_till >= ?3 AND " +
                "r.peopleCapacity>=?4");

        query.setParameter(1, city);
        query.setParameter(2, arrivalDate);
        query.setParameter(3, leaveDate);
        query.setParameter(4, peopleCapacity);

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
        return residences;
    }
}
