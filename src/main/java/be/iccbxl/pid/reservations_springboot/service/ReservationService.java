package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.Representation;
import be.iccbxl.pid.reservations_springboot.model.Reservation;
import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public List<Reservation> findAllByUser(User user) {
        return reservationRepository.findAllByUser(user);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable avec id " + id));
    }

    public Reservation bookReservation(User user, Representation rep, Long places) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRepresentation(rep);
        reservation.setPlaces(places);
        return reservationRepository.save(reservation);
    }

    public Reservation updatePlaces(Long reservationId, Long newPlaces) {
        Reservation reservation = findById(reservationId);
        reservation.setPlaces(newPlaces);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.delete(findById(reservationId));
    }

}