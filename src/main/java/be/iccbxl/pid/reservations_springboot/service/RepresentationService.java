package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.Location;
import be.iccbxl.pid.reservations_springboot.model.Representation;
import be.iccbxl.pid.reservations_springboot.model.Show;
import be.iccbxl.pid.reservations_springboot.repository.RepresentationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepresentationService {

    private final RepresentationRepository representationRepository;

    public RepresentationService(RepresentationRepository representationRepository) {
        this.representationRepository = representationRepository;
    }

    // Méthode pour récupérer toutes les représentations
    public List<Representation> getAll() {
        List<Representation> representations = new ArrayList<>();
        representationRepository.findAll().forEach(representations::add);
        return representations;
    }

    // Méthode pour récupérer une représentation par son ID
    public Representation getRepresenation(Long id) {
        return representationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Representation introuvable avec id: " + id));
    }

    // Méthode pour enregistrer une nouvelle représentation
    public void addRepresentation(Representation representation) {
        representationRepository.save(representation);
    }

    // Méthode pour mettre à jour une représentation existante
    public Representation updateRepresentation(Long id, Representation representation) {
        if (!representationRepository.existsById(id)) {
            throw new EntityNotFoundException("Echec de mise à jour, Representation introuvable avec id: " + id);
        }
        representation.setId(id);
        return representationRepository.save(representation);
    }

    // Méthode pour supprimer une représentation par son ID
    public void deleteRepresentation(Long id) {
        if (!representationRepository.existsById(id)) {
            throw new EntityNotFoundException("Echec de suppression. Representation introuvable avec id: " + id);
        }
        representationRepository.deleteById(id);
    }

    // Méthode pour récupérer les représentations par lieu
    public List<Representation> getFromLocation(Location location) {
        return representationRepository.findByLocation(location);
    }

    // Méthode pour récupérer les représentations par spectacle
    public List<Representation> getFromShow(Show show) {
        return representationRepository.findByShow(show);
    }
}
