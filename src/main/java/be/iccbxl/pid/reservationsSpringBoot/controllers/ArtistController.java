package be.iccbxl.pid.reservationsSpringBoot.controllers;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired // initialiser automatiquement l’attribut service par injection de dépendance
    ArtistService service; // Permettre d'utiliser les méthodes métiers pour acceder et manipuler les données
    @GetMapping("/artists" )
    public String index(Model model) {
        List<Artist> artists = service.getAllArtists(); // Obtenir la liste des artistes
        model.addAttribute( "artists", artists) ; // Ajouter au model l'attribut "artist" aui est associé à la liste d'artistes `artists
        model.addAttribute("title", "liste des artistes"); // Ajouter au model un autre attribut "title" avec la valeur "liste des artistes"
        return "artist/index";
    } // La méthode renvoie le chemin d’accès au template index.html

    @GetMapping("/artists/{id}") //Gère les requêtes HTTP GET pour un ID d'artiste spécifique.
    public String show(Model model, @PathVariable("id") long id) {
        Artist artist = service.getArtist(id);  // obtenir l'objet Artist sur base de l'ID fourni.
        model.addAttribute("artist", artist); // Ajouter l'objet Artist au modèle
        model.addAttribute("title", "Fiche d'un artiste");//Ajouter un attribut de titre au modèle
        return "artist/show"; //renvoie le nom logique de la vue à rendre
    } /* L’id de l’artiste est récupéré grâce à l’annotation @PathVariable
       * utilise un service pour obtenir les informations sur l'artiste correspondant à cet ID,
       * les ajoute au modèle et renvoie le nom logique de la vue à rendre.
       * La vue est associé à un fichier de vue Thymeleaf nommé "show.html" dans le dossier "artist"
       */


    @GetMapping("/artists/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);
        String referrer = request.getHeader("Referer"); //Générer le lien retour pour l'annulation
        if(referrer!=null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artists/"+artist.getId());
        }
        return "artist/edit";
    }// retrouver l’artiste au moyen de son id, puis l’envoyer au template artist/edit


    @PutMapping("/artists/{id}/edit")
    public String update(@Valid@ModelAttribute("artist") Artist artist, BindingResult bindingResult, @PathVariable("id") long id, Model model) {

        if (bindingResult.hasErrors()) {
            return "artist/edit";
        }

        Artist existing = service.getArtist(id);
        if(existing==null) {
            return "artist/index";
        }
        service.updateArtist(id, artist);

        return "redirect:/artists/"+artist.getId();
    }/* 1) Valider les données du formulaire grâce à l’annotation @Valid
      * 2) Avec @ModelAttribute, l’entité est automatiquement ajoutée au model
      * 3) Récupérer l’artiste à modifier et le mettons à jour
      * 4) Si les données pas validées (bindingResult.hasErrors()),
      * le contrôleur s’interrompt et redirection automatique vers le formulaire
      * pour afficher les messages d’erreur
      */


    @GetMapping("/artists/create")
    public String create(Model model) {
        Artist artist = new Artist(null,null);
        model.addAttribute("artist", artist);
        return "artist/create";
    }//envoie une entité Artist vide au template create.html .


    @PostMapping("/artists/create")
    public String store(@Valid@ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "artist/create";
        }
        service.addArtist(artist);

        return "redirect:/artists/"+artist.getId();
    }/* 1) La methode reçoit les données du formulaire dans l’entité artist en paramètre
     * 2) Avec @ModelAttribute, l’entité est automatiquement ajoutée au model
     * 3) @Valid permet d’exécuter les contraintes de validation spécifiées dans l’entité
     * 4) Puis persister l’entité au moyen de la couche service (addArtist)
     * 5) Enfin, il y a redirection pour afficher la nouvelle entité.
     */

    @DeleteMapping("/artists/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Artist existing = service.getArtist(id);
        if(existing!=null) {
            service.deleteArtist(id);
        }
        return "redirect:/artists";
    }

}


