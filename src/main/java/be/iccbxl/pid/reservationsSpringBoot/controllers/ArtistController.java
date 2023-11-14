package be.iccbxl.pid.reservationsSpringBoot.controllers;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ArtistController {

    // initialiser automatiquement l’attribut service par injection de dépendance
    @Autowired

    // Permettre d'utiliser les méthodes métiers pour acceder et manipuler les données
    ArtistService service;
    @GetMapping("/artists" )
    public String index(Model model) {

        // Obtenir la liste des artistes
        List<Artist> artists = service.getAllArtists();

        // Ajouter au model l'attribut "artist" aui est associé à la liste d'artistes `artists`
        model.addAttribute( "artists", artists) ;
            // Ajouter au model un autre attribut "title" avec la valeur "liste des artistes"
            model.addAttribute("title", "liste des artistes");

        return "artist/index";
    } // La méthode renvoie le chemin d’accès au template index.html

    @GetMapping("/artists/{id}") //Gère les requêtes HTTP GET pour un ID d'artiste spécifique.
    public String show(Model model, @PathVariable("id") long id) {
        Artist artist = service.getArtist(id);  // obtenir l'objet Artist sur base de l'ID fourni.

        model.addAttribute("artist", artist); // Ajouter l'objet Artist au modèle
        model.addAttribute("title", "Fiche d'un artiste");//Ajouter un attribut de titre au modèle

        return "artist/show"; //renvoie le nom logique de la vue à rendre
    } /* La méthode extrait l'ID de l'URI,
       * utilise un service pour obtenir les informations sur l'artiste correspondant à cet ID,
       * les ajoute au modèle et renvoie le nom logique de la vue à rendre.
       * La vue est associé à un fichier de vue Thymeleaf nommé "show.html" dans le dossier "artist"
       */
}


