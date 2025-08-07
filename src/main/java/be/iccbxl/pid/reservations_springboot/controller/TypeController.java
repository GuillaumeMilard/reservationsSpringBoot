package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.model.Type;
import be.iccbxl.pid.reservations_springboot.service.TypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    // Afficher la liste des types
    @GetMapping("/types")
    public String index(Model model) {
        List<Type> types = typeService.getAll();
        model.addAttribute("types", types);
        model.addAttribute("title", "Liste des types");
        return "type/index";
    }

    // Afficher un type dont l'id est passé en paramètre dans l'URL
    @GetMapping("/types/{id}")
    public String show(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {
        Type type = typeService.getType(id);
        if (type == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Type introuvable !");
            return "redirect:/types";
        }
        model.addAttribute("type", type);
        model.addAttribute("title", "Fiche d'un type");
        return "type/show";
    }

    // Afficher le formulaire de création d'un nouveau type
    @GetMapping("/types/create")
    public String create(Model model) {
        if (!model.containsAttribute("type")) {
            model.addAttribute("type", new Type());
        }
        return "type/create";
    }


    @PostMapping("/types/create")
    public String store(@Valid @ModelAttribute Type type, BindingResult bindingResult,
                        Model model, RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la création du type d'artiste !");
            return "type/create";
        }
        typeService.addType(type);
        redirAttrs.addFlashAttribute("successMessage", "Type créé avec succès.");
        return "redirect:/types/" + type.getId();
    }

//    // Enregistrer un nouveau type
//    @PostMapping("/types")
//    public String store(Type type) {
//        typeService.addType(type);
//        return "redirect:/types/" + type.getId();
//    }



    // Afficher le formulaire d'édition d'un type dont l'id est passé en paramètre dans l'URL
    @GetMapping("/types/{id}/edit")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {
        Type type = typeService.getType(id);
        if (type == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Type introuvable !");
            return "redirect:/types";
        }
        model.addAttribute("type", type);
        model.addAttribute("back", "/types/" + type.getId());
        return "type/edit";
    }

    // Mettre à jour le formulaire d'édition d'un type
    @PutMapping("/types/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Type type,
                         BindingResult bindingResult, RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            return "type/edit";
        }
        if (typeService.getType(id) == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Type introuvable !");
            return "redirect:/types";
        }
        typeService.updateType(id, type);
        redirAttrs.addFlashAttribute("successMessage", "Type modifié avec succès.");
        return "redirect:/types/" + id;
    }

//    // Mettre à jour le formulaire d'édition d'un type
//    @PutMapping("/types/{id}")
//    public String update(@PathVariable Long id, @ModelAttribute Type type) {
//        typeService.updateType(id, type);
//        return "redirect:/types/" + id;
//    }


    // Supprimer un type dont l'id est passé en paramètre dans l'URL
    @DeleteMapping("/types/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirAttrs) {
        if (typeService.getType(id) != null) {
            typeService.deleteType(id);
            redirAttrs.addFlashAttribute("successMessage", "Type supprimé avec succès.");
        } else {
            redirAttrs.addFlashAttribute("errorMessage", "Type introuvable !");
        }
        return "redirect:/types";
    }
}
