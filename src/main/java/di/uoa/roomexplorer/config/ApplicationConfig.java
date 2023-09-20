package di.uoa.roomexplorer.config;

import di.uoa.roomexplorer.exception.ResidenceNotFoundException;
import di.uoa.roomexplorer.model.*;
import di.uoa.roomexplorer.repositories.*;
import di.uoa.roomexplorer.services.MatrixFactorizationService;
import lombok.RequiredArgsConstructor;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static di.uoa.roomexplorer.model.RoomType.*;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initAdmin(AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            Admin admin = new Admin("admin", passwordEncoder.encode("1234"), "Admin", "Admin", "admin@mail.com", "6986534214");
            adminRepo.save(admin);

            System.out.println("Admin username: admin");
            System.out.println("Admin password: 1234");

        };
    }

    @Bean
    public CommandLineRunner initHost(HostRepo hostRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            Host host = new Host("giannis22", passwordEncoder.encode("2207"), "Giannis", "Triantafyllopoulos", "giannis22@gmail.com", "6986534214", "https://a1.muscache.com/ac/users/748818/profile_pic/1384667836/original.jpg?interpolation=lanczos-none&crop=w:w;*,*&crop=h:h;*,*&resize=50:*&output-format=jpg&output-quality=70");
            host.setApproved(true);
            hostRepo.save(host);

            host = new Host("anna19", passwordEncoder.encode("1907"), "Anna", "Kosmidi", "anna19@gmail.com", "6986534213");
            hostRepo.save(host);
        };
    }

    @Bean
    public CommandLineRunner initRenter(RenterRepo renterRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            Renter renter = new Renter("aggelos10", passwordEncoder.encode("1234"), "Aggelos", "Gounelas", "aggelos10@gmail.com", "6986534211");
            renterRepo.save(renter);

            renter = new Renter("lydia10", passwordEncoder.encode("0000"), "Lydia", "Kyriakou", "lydia10@gmail.com", "6986534212");
            renterRepo.save(renter);

            renter = new Renter("aggelos20", passwordEncoder.encode("1234"), "Aggelos", "Gounelas", "aggelos20@gmail.com", "6986534211");
            renterRepo.save(renter);

//            renter = new Renter("lydia20", passwordEncoder.encode("0000"), "Lydia", "Kyriakou", "lydia20@gmail.com", "6986534212");
//            renterRepo.save(renter);
        };
    }

    @Bean
    public CommandLineRunner initResidence(ResidenceRepo residenceRepo, HostRepo hostRepo) {
        return args -> {

            Residence residence = new Residence(100.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(101.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(102.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), true, true, true, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(103.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), true, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(104.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, SHARED, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, true, true);
            residenceRepo.save(residence);

            residence = new Residence(105.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, HOUSE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, true, true, true, false);
            residenceRepo.save(residence);

            residence = new Residence(106.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, SHARED, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(107.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, HOUSE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, true, true, false, false, false, false);
            residenceRepo.save(residence);


            residence = new Residence(70.0, "Patra", "Patra","Patra 19", 0.0, 0.0, 4, 5, 4, 1, 1, 35, SHARED, 0, 1, hostRepo.findById(1L).get(), LocalDate.of(2023, 5, 1), LocalDate.of(2024, 8, 31), true, true, false, true, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(108.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), true, true, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(109.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(110.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(111.0, "Athens", "Byron","Kalipoleos 2", 0.0, 0.0, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(120.0, "Thessaloniki", "Thessaloniki","Thessaloniki 19", 0.0, 0.0, 1, 2, 2, 1, 1, 35, PRIVATE, 0, 1, hostRepo.findById(2L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2024, 3, 10), false, false, false, false, false, true, false, false);
            residenceRepo.save(residence);

            residence = new Residence(50.0, "Volos", "Volos","Volos 19", 0.0, 0.0, 4, 5, 2, 1, 1, 35, SHARED, 0, 1, hostRepo.findById(2L).get(), LocalDate.of(2023, 5, 1), LocalDate.of(2024, 8, 31), true, true, false, true, false, false, false, false);
            residenceRepo.save(residence);
        };
    }

    @Bean
    public CommandLineRunner initReservation(ReservationRepo reservationRepo, RenterRepo renterRepo, ResidenceRepo residenceRepo) {
        return args -> {

            Reservation reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 2, 15), LocalDate.of(2023, 2, 20), ReservationState.PENDING, residenceRepo.findById(1L).get(), renterRepo.findById(1L).get(), 3, "");
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 2, 10), LocalDate.of(2023, 2, 14), ReservationState.PENDING, residenceRepo.findById(3L).get(), renterRepo.findById(1L).get(), 4, "");
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 2, 22), LocalDate.of(2023, 2, 27), ReservationState.PENDING, residenceRepo.findById(5L).get(), renterRepo.findById(2L).get(), 2, "");
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 20), ReservationState.PENDING, residenceRepo.findById(9L).get(), renterRepo.findById(1L).get(), 1, "");
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 3, 2), LocalDate.of(2023, 3, 8), ReservationState.PENDING, residenceRepo.findById(14L).get(), renterRepo.findById(2L).get(), 5, "");
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 3, 2), LocalDate.of(2023, 3, 8), ReservationState.PENDING, residenceRepo.findById(7L).get(), renterRepo.findById(3L).get(), 2, "");
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 3, 2), LocalDate.of(2023, 3, 8), ReservationState.PENDING, residenceRepo.findById(15L).get(), renterRepo.findById(3L).get(), 4, "");
            reservationRepo.save(reservation);
        };
    }

    @Bean
    @Autowired
    public CommandLineRunner initMF(MatrixFactorizationService matrixFactorizationService, RenterRepo renterRepo, ResidenceRepo residenceRepo, ReservationRepo reservationRepo) {
        return args -> {

            List<Long> renters = renterRepo.findAllRenterId();
            List<Long> residences = residenceRepo.findAllResidenceId();

            Matrix matrix = Matrix.zero(renters.size(), residences.size());
            matrixFactorizationService.init(matrix);

            int renterCount = renters.size();
            for (int renterIndex = 0; renterIndex < renterCount; renterIndex++) {
                System.out.println("renter " + renterIndex);
                Long renterId = renters.get(renterIndex);
                List<Reservation> reservations = reservationRepo.findReservationsByRenter_Id(renterId).orElseThrow(
                        ()-> new ResidenceNotFoundException("Reservations for renter with id" + renterId + "was not found")
                );

                int reservationCount = reservations.size();
                for (int reservationIndex = 0; reservationIndex < reservationCount; reservationIndex++) {
                    Reservation reservation = reservations.get(reservationIndex);
                    int residenceIndex = residences.indexOf(reservation.getResidence().getId());
                    matrixFactorizationService.updateCellIndex(renterIndex, residenceIndex, reservation.getStars());
                }
            }

            matrixFactorizationService.train();
            System.out.println("finished training");
            System.out.println(matrixFactorizationService.getMatrix().toString());
        };
    }
}
