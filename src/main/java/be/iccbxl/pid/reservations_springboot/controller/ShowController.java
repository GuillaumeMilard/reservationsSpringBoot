package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.model.Artist;
import be.iccbxl.pid.reservations_springboot.model.ArtistType;
import be.iccbxl.pid.reservations_springboot.model.Show;
import be.iccbxl.pid.reservations_springboot.service.ShowService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    // --- Afficher la liste des spectacles ---//
    @GetMapping("/shows")
    public String index(Model model) {
        List<Show> shows = showService.getAll();
        model.addAttribute("shows", shows);
        model.addAttribute("title", "Liste des spectacles");
        return "show/index";// => templates/show/index.html
    }

    // --- Afficher le détail d'un spectacle ---//
    @GetMapping("/shows/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        Show show = showService.getShow(id);
        Map<String, List<Artist>> collaborateurs = new TreeMap<>();
        for (ArtistType at : show.getArtistTypes()) {
            String type = at.getType().getName();
            collaborateurs
                    .computeIfAbsent(type, k -> new ArrayList<>())
                    .add(at.getArtist());
        }
        model.addAttribute("collaborateurs", collaborateurs);
        model.addAttribute("show", show);
        model.addAttribute("title", "Fiche Spectacle");
        return "show/show";
    }

    // --- Afficher le formulaire de création de spectacle ---
    @GetMapping("/shows/create")
    public String createForm(Model model) {
        model.addAttribute("show", new Show());
        model.addAttribute("title", "Nouveau Spectacle");
        return "show/create"; // => templates/show/create.html
    }

    // --- Créer un spectacle---
    @PostMapping("/shows")
    public String create(@ModelAttribute("show") Show show) {
        showService.addShow(show);
        return "redirect:/shows"; // redirige vers la liste des spectacles
    }

    // --- Afficher le formulaire de modification de spectacle ---
    @GetMapping("/shows/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        Show show = showService.getShow(id);
        model.addAttribute("show", show);
        model.addAttribute("title", "Modifier Spectacle");
        return "show/edit"; // => templates/show/edit.html
    }

    // --- Mettre à jour le formulaire de spectacle modifié ---
    @PostMapping("/shows/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("show") Show updatedShow) {
        showService.updateShow(id, updatedShow);
        return "redirect:/shows/" + id;
    }

    // --- Supprimer un spectacle ---
    @PostMapping("/shows/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        showService.deleteShow(id);
        return "redirect:/shows";
    }


}
