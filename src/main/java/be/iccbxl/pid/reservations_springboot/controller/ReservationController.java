package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.model.Representation;
import be.iccbxl.pid.reservations_springboot.model.Reservation;
import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.service.RepresentationService;
import be.iccbxl.pid.reservations_springboot.service.ReservationService;
import be.iccbxl.pid.reservations_springboot.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
@PreAuthorize("isAuthenticated()")
public class ReservationController {

    private final RepresentationService representationService;
    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(RepresentationService representationService, ReservationService reservationService, UserService userService) {
        this.representationService = representationService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    // Afficher toutes les réservations de l'utilisateur connecté
    @GetMapping
    public String index(Model model, Authentication authentication) {
        User user = userService.getUserByLogin(authentication.getName());
        model.addAttribute("reservations", reservationService.findAllByUser(user));
        return "reservation/index";
    }

    // Afficher les détails d'une réservation spécifique
   @GetMapping("/{id}")
   public String show(@PathVariable Long id, Model model) {
       model.addAttribute("reservation", reservationService.findById(id));
       return "reservation/show";
   }

   // Afficher un formulaire pour modifier le nombre de places
   @GetMapping("/{id}/edit")
   public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "reservation/edit";
   }

   // Traiter la modification du nombre de places (POST avec _method=PUT)
   @PostMapping("/{id}/update")
   public String update(@PathVariable Long id, @ModelAttribute Reservation reservation) {
       reservationService.updatePlaces(id, reservation.getPlaces());
       return "redirect:/reservations";
   }

   // Réserver des places pour une représentation
   @PostMapping("/book")
   public String book(@RequestParam Long representationId,
                        @RequestParam Long places,
                        Authentication authentication) {
       User user = userService.getUserByLogin(authentication.getName());
       Representation rep = representationService.getRepresentation(representationId);
       reservationService.bookReservation(user, rep, places);
       return "redirect:/reservations";
   }
}
