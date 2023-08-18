package di.uoa.roomexplorer.config;

import di.uoa.roomexplorer.model.*;
import di.uoa.roomexplorer.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

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

            Host host = new Host("giannis", passwordEncoder.encode("2207"), "Giannis", "Triantafyllopoulos", "giannis@gmail.com", "6986534214");
            host.setApproved(true);
            hostRepo.save(host);

            host = new Host("anna", passwordEncoder.encode("1907"), "Anna", "Kosmidi", "anna@gmail.com", "6986534213");
            hostRepo.save(host);
        };
    }

    @Bean
    public CommandLineRunner initRenter(RenterRepo renterRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            Renter renter = new Renter("aggelos", passwordEncoder.encode("1234"), "Aggelos", "Gounelas", "aggelos@gmail.com", "6986534211");
            renterRepo.save(renter);

            renter = new Renter("lydia", passwordEncoder.encode("0000"), "Lydia", "Kyriakou", "lydia@gmail.com", "6986534212");
            renterRepo.save(renter);
        };
    }

    @Bean
    public CommandLineRunner initResidence(ResidenceRepo residenceRepo, HostRepo hostRepo) {
        return args -> {

            Residence residence = new Residence(100.0, "Athens", 15773, 2, 4, 2, 1, 1, 35, PRIVATE, 34345, 5, hostRepo.findById(1L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2023, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(70.0, "Patra", 19752, 4, 5, 4, 1, 1, 35, SHARED, 0, 1, hostRepo.findById(1L).get(), LocalDate.of(2023, 5, 1), LocalDate.of(2023, 8, 31), true, true, false, true, false, false, false, false);
            residenceRepo.save(residence);


            residence = new Residence(120.0, "Thessaloniki", 20194, 1, 2, 2, 1, 1, 35, PRIVATE, 0, 1, hostRepo.findById(2L).get(), LocalDate.of(2023, 2, 10), LocalDate.of(2023, 3, 10), false, false, false, false, false, false, false, false);
            residenceRepo.save(residence);

            residence = new Residence(50.0, "Volos", 19752, 4, 5, 2, 1, 1, 35, SHARED, 0, 1, hostRepo.findById(2L).get(), LocalDate.of(2023, 5, 1), LocalDate.of(2023, 8, 31), true, true, false, true, false, false, false, false);
            residenceRepo.save(residence);
        };
    }

    @Bean
    public CommandLineRunner initReservation(ReservationRepo reservationRepo, RenterRepo renterRepo, ResidenceRepo residenceRepo) {
        return args -> {

            Reservation reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 2, 15), LocalDate.of(2023, 2, 20), ReservationState.PENDING, residenceRepo.findById(1L).get(), renterRepo.findById(1L).get());
            reservationRepo.save(reservation);
            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 2, 10), LocalDate.of(2023, 2, 14), ReservationState.PENDING, residenceRepo.findById(1L).get(), renterRepo.findById(1L).get());
            reservationRepo.save(reservation);
            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 2, 22), LocalDate.of(2023, 2, 27), ReservationState.PENDING, residenceRepo.findById(1L).get(), renterRepo.findById(1L).get());
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 20), ReservationState.PENDING, residenceRepo.findById(2L).get(), renterRepo.findById(1L).get());
            reservationRepo.save(reservation);

            reservation = new Reservation(LocalDate.now(), LocalDate.of(2023, 3, 2), LocalDate.of(2023, 3, 8), ReservationState.PENDING, residenceRepo.findById(3L).get(), renterRepo.findById(2L).get());
            reservationRepo.save(reservation);
        };
    }
}
