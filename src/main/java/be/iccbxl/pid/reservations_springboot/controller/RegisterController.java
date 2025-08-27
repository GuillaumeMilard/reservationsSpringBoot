package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.dto.UserCreateDTO;
import be.iccbxl.pid.reservations_springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("languages")
    public List<String> languages() {
    // Alimente la liste déroulante (adapter selon votre besoin)
        return List.of("fr", "en", "nl", "de");
    }

    // Afficher le formulaire d'inscription
    @GetMapping("/register")
    public String registerForm(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/"; // déjà connecté → page d'accueil
        }
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreateDTO());
        }
        return "authentication/register";
    }

    // Traiter le formulaire d'inscription
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserCreateDTO dto,
                           BindingResult bindingResult,
                           RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("errorMessage", "Le formulaire est incomplet ou contient des erreurs.");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            ra.addFlashAttribute("user", dto);
            return "redirect:/register";
        }

        try {
            userService.registerUser(dto);

        } catch (UserService.PasswordsDoNotMatchException e) {
            ra.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            ra.addFlashAttribute("user", dto);
            return "redirect:/register";

        } catch (UserService.EmailAlreadyUsedException e) {
            ra.addFlashAttribute("errorMessage", "Cet e-mail est déjà utilisé.");
            ra.addFlashAttribute("user", dto);
            return "redirect:/register";

        } catch (UserService.LoginAlreadyUsedException e) {
            ra.addFlashAttribute("errorMessage", "Ce login est déjà pris.");
            ra.addFlashAttribute("user", dto);
            return "redirect:/register";
        }

        ra.addFlashAttribute("successMessage", "Votre compte a été créé avec succès !");
        return "redirect:/login";
    }

}
