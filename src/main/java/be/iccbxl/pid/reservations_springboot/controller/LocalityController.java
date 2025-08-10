package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.exception.DuplicateFieldException;
import be.iccbxl.pid.reservations_springboot.model.Locality;
import be.iccbxl.pid.reservations_springboot.service.LocalityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LocalityController {

    private final LocalityService localityService;

    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    // Afficher la liste des localités
    @GetMapping("/localities")
    public String index(Model model) {
        model.addAttribute("localities", localityService.getAll());
        model.addAttribute("title", "Liste des localités");
        return "locality/index";
    }


    // Afficher une localité dont l'id est passé en paramètre dans l'URL
    @GetMapping("/localities/{id}")
    public String show(@PathVariable Long id,
                       Model model,
                       RedirectAttributes redirAttrs) {

        Optional<Locality> optionalLocality = localityService.getLocality(id);
        if (optionalLocality.isEmpty()) {
            redirAttrs.addFlashAttribute("errorMessage",
                    "Localité introuvable !");
            return "redirect:/localities";
        }
        // Si la localité existe, on l'ajoute au modèle
        Locality locality = optionalLocality.get();
        model.addAttribute("locality", locality);
        model.addAttribute("title", "Fiche localité");
        return "locality/show";
    }


    // Afficher le formulaire de création d'une nouvelle localité
    @GetMapping("/localities/create")
    public String create(Model model) {
        if (!model.containsAttribute("locality")) {
            model.addAttribute("locality", new Locality());
        }
        return "locality/create";
    }


    //Enregistrer une nouvelle localité
    @PostMapping("/localities/create")
    public String store(@Valid Locality locality,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage",
                    "Échec de la création de la localité !");
            return "locality/create";
        }
        try {
            localityService.addLocality(locality);
            redirAttrs.addFlashAttribute("successMessage",
                    "Localité créée avec succès !");
            return "redirect:/localities/" + locality.getId();

        } catch (DuplicateFieldException dfe) {
            bindingResult.rejectValue(
                    dfe.getField(),
                    "unique",
                    dfe.getField().equals("postalCode")
                            ? "Ce code postal existe déjà."
                            : "Ce nom de localité existe déjà."
            );
            model.addAttribute("errorMessage",
                    "Échec de la création de la localité !");
            return "locality/create";
        }
    }


    // Afficher le formulaire de modification d'une localité
    @GetMapping("/localities/{id}/edit")
    public String edit(@PathVariable Long id,
                       Model model,
                       RedirectAttributes redirAttrs) {
        Optional<Locality> optionalLocality = localityService.getLocality(id);
        if (optionalLocality.isEmpty()) {
            redirAttrs.addFlashAttribute("errorMessage",
                    "Localité introuvable !");
            return "redirect:/localities";
        }
        Locality locality = optionalLocality.get();
        model.addAttribute("locality", locality);
        model.addAttribute("back",
                "/localities/" + locality.getId());
        return "locality/edit";
    }

    @PutMapping("/localities/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("locality") Locality locality,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage",
                    "Échec de la modification !");
            return "locality/edit";
        }

        Optional<Locality> existingLocality = localityService.getLocality(id);
        if (existingLocality.isEmpty()) {
            redirect.addFlashAttribute("errorMessage",
                    "Localité introuvable !");
            return "redirect:/localities";
        }
        // Si la localité existe, faire la mise à jour
        try {
            // s’assurer qu’on met à jour la bonne entité
            locality.setId(id);
            localityService.updateLocality(id, locality);
            redirect.addFlashAttribute("successMessage",
                    "Localité mise à jour avec succès !");
            return "redirect:/localities/" + id;

        } catch (DuplicateFieldException dfe) {
            bindingResult.rejectValue(
                    dfe.getField(),
                    "unique",
                    dfe.getField().equals("postalCode")
                            ? "Ce code postal existe déjà."
                            : "Ce nom de localité existe déjà."
            );
            model.addAttribute("errorMessage",
                    "Échec de la modification !");
            return "locality/edit";
        }
    }


//    // Enregistrer les modifications d'une localité
//    @PutMapping("/localities/{id}")
//    public String update(@PathVariable Long id,
//                         @Valid Locality locality,
//                         BindingResult bindingResult,
//                         RedirectAttributes redirAttrs) {
//        if (bindingResult.hasErrors()) {
//            return "locality/edit";
//        }
//        Optional<Locality> existingLocality = localityService.getLocality(id);
//        if (existingLocality.isEmpty()) {
//            redirAttrs.addFlashAttribute("errorMessage",
//                    "Localité introuvable !");
//            return "redirect:/localities";
//        }
//        localityService.updateLocality(id, locality);
//        redirAttrs.addFlashAttribute("successMessage",
//                    "Localité mise à jour avec succès !");
//        return "redirect:/localities/" + id;
//    }


    // Supprimer une localité
    @DeleteMapping("/localities/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirAttrs) {
        Optional<Locality> Locality = localityService.getLocality(id);
        if (Locality.isPresent()) {
            localityService.deleteLocality(id);
            redirAttrs.addFlashAttribute("successMessage",
                    "Localité supprimée avec succès !");
        } else {
            redirAttrs.addFlashAttribute("errorMessage",
                    "Localité introuvable !");
        }
        return "redirect:/localities";
    }

}