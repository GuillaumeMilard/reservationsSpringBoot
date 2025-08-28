package be.iccbxl.pid.reservations_springboot.config;

import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;

    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    // Fournit la liste des langues disponibles (clé = code, valeur = libellé)
    @ModelAttribute("languages")
    public Map<String, String> languages() {
        Map<String, String> langues = new LinkedHashMap<>();
        langues.put("fr", "Français");
        langues.put("en", "English");
        langues.put("nl", "Nederlands");
        langues.put("de", "Deutsch");
        return langues;
    }

    @ModelAttribute("user")
    public User addUserToModel(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return userService.getUserByLogin(authentication.getName());
        }
        return null;
    }
}
