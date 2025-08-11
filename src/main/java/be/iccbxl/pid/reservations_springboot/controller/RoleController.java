package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.exception.DuplicateFieldException;
import be.iccbxl.pid.reservations_springboot.model.Role;
import be.iccbxl.pid.reservations_springboot.model.UserRole;
import be.iccbxl.pid.reservations_springboot.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
@Controller
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * INDEX - liste tous les rôles
     */
    @GetMapping("/roles")
    public String index(Model model) {
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("title", "Liste des rôles");
        return "role/index";
    }

    /**
     * SHOW - détail d’un rôle dont l'id est passé dans l'URL
     */
    @GetMapping("/roles/{id}")
    public String show(@PathVariable Long id,
                       Model model,
                       RedirectAttributes redirAttrs) {
        // Récupérer le rôle dont l'id est passé en paramètre dans l'URL
        // Si le rôle n'existe pas, on redirige vers la liste des rô
        Optional<Role> optionalRole = roleService.getRole(id);
        if (optionalRole.isEmpty()) {
            redirAttrs.addFlashAttribute("errorMessage",
                    "Rôle introuvable !");
            return "redirect:/roles";
        }
        // Si le rôle existe, on l'ajoute au modèle
        model.addAttribute("role", optionalRole.get());
        model.addAttribute("title", "Fiche rôle");
        return "role/show";
    }

    /**
     * CREATE - formulaire création vide
     */
    @GetMapping("/roles/create")
    public String create(Model model) {
        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new Role());
        }
        return "role/create";
    }

    /**
     * STORE - enregistrement d’un nouveau rôle
     */
    @PostMapping("/roles")
    public String store(@Valid Role role,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("errorMessage",
                    "Echec de la création du role !");
            return "redirect:/roles/create";
        }
        try {
            roleService.addRole(role);
            redirAttrs.addFlashAttribute("successMessage",
                    "Rôle créé avec succès !");
            return "redirect:/roles";
        } catch (DuplicateFieldException dfe) {
            bindingResult.rejectValue(
                    dfe.getField(),
                    "unique",
                    "Le rôle " + dfe.getField() + " existe déjà.");
            model.addAttribute("userRoles", UserRole.values());
            return "roles/create";
        }
    }

    /**
     * EDIT - formulaire édition d’un rôle dont l'id est passé dans l'URL
     */
    @GetMapping("/roles/{id}/edit")
    public String edit(@PathVariable Long id,
                       Model model,
                       RedirectAttributes redirAttrs) {
        // Récupérer le rôle dont l'id est passé en paramètre dans l'URL
        // Si le rôle n'existe pas, on redirige vers la liste des roles
        Optional<Role> optionalRole = roleService.getRole(id);
        if (optionalRole.isEmpty()) {
            redirAttrs.addFlashAttribute("errorMessage",
                    "Rôle introuvable !");
            return "redirect:/roles";
        }
        // Si le rôle existe, on l'ajoute au modèle
        model.addAttribute("role", optionalRole.get());
        model.addAttribute("userRoles", UserRole.values());
        return "role/edit";
    }

    /**
     * UPDATE - mise à jour d’un rôle dont l'id est passé dans l'URL
     */
    @PostMapping("/roles/{id}")
    public String update(@PathVariable Long id,
                         @Valid Role role,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("errorMessage",
                    "Echec de la mise à jour du rôle !");
            return "role/edit";
        }
        try {
            roleService.updateRole(id, role);
            redirAttrs.addFlashAttribute("successMessage",
                    "Rôle mis à jour avec succès !");
            return "redirect:/roles";
        } catch (DuplicateFieldException dfe) {
            bindingResult.rejectValue(
                    dfe.getField(),
                    "unique",
                    "Le rôle " + dfe.getField() + " existe déjà.");
            model.addAttribute("userRoles", UserRole.values());
            return "role/edit";
        }
    }

    /**
     * DELETE - suppression d’un rôle dont l'id est passé dans l'URL
     */
    @PostMapping("/roles/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirAttrs) {
        // Récupérer le rôle dont l'id est passé en paramètre dans l'URL
        // Si le rôle n'existe pas, on redirige vers la liste des roles
        Optional<Role> optionalRole = roleService.getRole(id);
        if (optionalRole.isEmpty()) {
            redirAttrs.addFlashAttribute("errorMessage",
                    "Rôle introuvable !");
            return "redirect:/roles";
        }
        // Si le rôle existe, on le supprime
        roleService.deleteRole(id);
        redirAttrs.addFlashAttribute("successMessage",
                "Rôle supprimé avec succès !");
        return "redirect:/roles";
    }

}
