//package be.iccbxl.pid.reservations_springboot.controller;
//
//import be.iccbxl.pid.reservations_springboot.model.User;
//import be.iccbxl.pid.reservations_springboot.service.UserService;
//import be.iccbxl.pid.reservations_springboot.dto.ProfileUpdateDTO;
//import jakarta.validation.Valid;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/authentication/profile")
//public class ProfileController {
//
//    private final UserService userService;
//
//    public ProfileController(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * Affiche le profil de l’utilisateur connecté
//     */
//    @GetMapping
//    public String profile(Authentication authentication, Model model) {
//        User user = userService.getUserByLogin(authentication.getName());
//        model.addAttribute("user", user);
//        return "authentication/profile";
//    }
//
//    /**
//     * Affiche le formulaire de modification (autorisé uniquement pour soi-même ou ADMIN)
//     */
//    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isSelf(#id, authentication)")
//    @GetMapping("/{id}/edit")
//    public String editProfile(@PathVariable Long id, Model model) {
//        User user = userService.getUser(id);
//        model.addAttribute("userId", user.getId());
//        model.addAttribute("form", ProfileUpdateDTO.fromUser(user));
//        return "authentication/profile_edit";
//    }
//
//    /**
//     * Traite la soumission du formulaire de modification (autorisé uniquement pour soi-même ou ADMIN)
//     */
//    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isSelf(#id, authentication)")
//    @PostMapping("/{id}/edit")
//    public String updateProfile(@PathVariable Long id,
//                                @Valid @ModelAttribute("profileUpdateDTO") ProfileUpdateDTO profileUpdateDTO,
//                                BindingResult bindingResult,
//                                Model model,
//                                RedirectAttributes ra) {
//        // Vérifie la validation des champs
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("userId", id);
//            return "authentication/profile_edit";
//        }
//
//        // Met à jour l’utilisateur avec les données du DTO
//        User user = userService.getUser(id);
//        profileUpdateDTO.applyToUser(user);
//        userService.addUser(user);
//
//        // Message de succès
//        ra.addFlashAttribute("successMessage", "Profil mis à jour avec succès.");
//        return "redirect:/authentication/profile";
//    }
//
//    /**
//     * Supprime le compte (autorisé uniquement pour soi-même ou ADMIN)
//     */
//    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isSelf(#id, authentication)")
//    @PostMapping("/{id}/delete")
//    public String deleteOwnAccount(@PathVariable Long id) {
//        userService.deleteOwnAccount(id);
//        return "redirect:/logout"; // redirige vers logout après suppression
//    }
//}


package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.dto.ProfileUpdateDTO;
import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authentication/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Affiche le profil de l’utilisateur connecté
     */
    @GetMapping
    public String profile(Authentication auth, Model model) {
        User user = userService.getUserByLogin(auth.getName());
        model.addAttribute("user", user);
        return "authentication/profile";
    }

    /**
     * Affiche le formulaire de modification (autorisé uniquement pour soi-même ou ADMIN)
     */
    @GetMapping("/{id}/edit")
    public String editProfile(@PathVariable Long id, Authentication auth, Model model) {
        User current = userService.getUserByLogin(auth.getName());

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!current.getId().equals(id) && !isAdmin) {
            throw new AccessDeniedException("Vous ne pouvez pas modifier ce profil");
        }

        User user = userService.getUser(id);
        model.addAttribute("userId", user.getId());
        model.addAttribute("form", ProfileUpdateDTO.fromUser(user));
        return "authentication/profile_edit";
    }

    /**
     * Traite la soumission du formulaire de modification (autorisé uniquement pour soi-même ou ADMIN)
     */
    @PostMapping("/{id}/edit")
    public String updateProfile(@PathVariable Long id,
                                @Valid @ModelAttribute("form") ProfileUpdateDTO form,
                                BindingResult bindingResult,
                                Authentication auth,
                                Model model,
                                RedirectAttributes ra) {

        User current = userService.getUserByLogin(auth.getName());

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!current.getId().equals(id) && !isAdmin) {
            throw new AccessDeniedException("Vous ne pouvez pas modifier ce profil");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", id);
            return "authentication/profile_edit";
        }

        User user = userService.getUser(id);
        form.applyToUser(user);
        userService.addUser(user);

        ra.addFlashAttribute("successMessage", "Profil mis à jour avec succès.");
        return "redirect:/authentication/profile";
    }

    /**
     * Supprime le compte (autorisé uniquement pour soi-même ou ADMIN)
     */
    @PostMapping("/{id}/delete")
    public String deleteOwnAccount(@PathVariable Long id, Authentication auth) {
        User current = userService.getUserByLogin(auth.getName());

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!current.getId().equals(id) && !isAdmin) {
            throw new AccessDeniedException("Vous ne pouvez pas supprimer ce compte");
        }

        userService.deleteOwnAccount(id);
        return "redirect:/logout"; // déconnexion après suppression
    }
}
