package be.iccbxl.pid.reservations_springboot.repository;

import be.iccbxl.pid.reservations_springboot.model.Reservation;
import be.iccbxl.pid.reservations_springboot.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    // Récupère toutes les réservations pour un utilisateur donné
    List<Reservation> findAllByUser(User user);

    // Variante si tu préfères chercher directement avec l'ID
    List<Reservation> findAllByUserId(Long userId);

    Reservation findById(long id);


}
