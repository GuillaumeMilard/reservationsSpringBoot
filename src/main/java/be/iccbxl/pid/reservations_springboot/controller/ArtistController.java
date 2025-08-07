package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.model.Artist;
import be.iccbxl.pid.reservations_springboot.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    ArtistService service;

    // Afficher la liste des artistes
    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("artists", artists);
        model.addAttribute("title", "Liste des artistes");
        return "artist/index";
    }

    // Afficher un artiste dont l'id est passé en paramètre dans l'URL
    @GetMapping("/artists/{id}")
    public String show(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {
        Artist artist = service.getArtist(id);
        if (artist == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Artiste introuvable !");
            return "redirect:/artists";
        }
        model.addAttribute("artist", artist);
        model.addAttribute("title", "Fiche d'un artiste");
        return "artist/show";
    }

    // Afficher le formulaire d'édition d'un artiste dont l'id est passé en paramètre dans l'URL
    @GetMapping("/artists/{id}/edit")
    public String edit(Model model, @PathVariable Long id, HttpServletRequest request) {
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);
        //Générer le lien retour pour l'annulation
        String referrer = request.getHeader("Referer");
        if(referrer!=null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artists/"+artist.getId());
        }
        return "artist/edit";
    }

    // Mettre à jour le formulaire d'édition d'un artiste
    @PutMapping("/artists/{id}/edit")
    public String update(@Valid @ModelAttribute Artist artist, BindingResult bindingResult,
                         @PathVariable Long id, Model model, RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Echec de la modification de l'artiste !");
            return "artist/edit";
        }
        Artist existing = service.getArtist(id);
        if(existing==null) {
            return "artist/index";
        }
        service.updateArtist(id, artist);
        redirAttrs.addFlashAttribute("successMessage", "Artiste modifié avec succès.");
        return "redirect:/artists/"+artist.getId();
    }

    // Afficher le formulaire de création d'un artiste
    @GetMapping("/artists/create")
    public String create(Model model) {
        if (!model.containsAttribute("artist")) {
            model.addAttribute("artist", new Artist());
        }
        return "artist/create";
    }

    // Enregistrer un nouvel artiste
    @PostMapping("/artists/create")
    public String store(@Valid @ModelAttribute Artist artist, BindingResult bindingResult,
                        Model model, RedirectAttributes redirAttrs) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la création de l'artiste !");
            return "artist/create";
        }
        service.addArtist(artist);
        redirAttrs.addFlashAttribute("successMessage", "Artiste créé avec succès.");
        return "redirect:/artists/"+ artist.getId();
    }

    // Supprimer un artiste dont l'id est passé en paramètre dans l'URL
    @DeleteMapping("/artists/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirAttrs) {
        Artist existing = service.getArtist(id);
        if(existing!=null) {
            service.deleteArtist(id);
            redirAttrs.addFlashAttribute("successMessage", "Artiste supprimé avec succès.");
        }else {
            redirAttrs.addFlashAttribute("errorMessage", "Échec de la suppression de l'artiste !");
        }
        return "redirect:/artists";
    }


}
