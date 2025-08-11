package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.exception.DuplicateFieldException;
import be.iccbxl.pid.reservations_springboot.model.Locality;
import be.iccbxl.pid.reservations_springboot.repository.LocalityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.iccbxl.pid.reservations_springboot.util.ValidationUtils.*;

@Service
public class LocalityService {

    private final LocalityRepository localityRepository;

    public LocalityService(LocalityRepository localityRepository) {
        this.localityRepository = localityRepository;
    }

    public List<Locality> getAll() {
        List<Locality> localities = new ArrayList<>();
        localityRepository.findAll().forEach(localities::add); // Conversion Iterable -> List
        return localities;
    }

    public Optional<Locality> getLocality(Long id) {
        return localityRepository.findById(id);
    }

    @Transactional
    public void addLocality(Locality locality) {

        requireNonNull(locality, "Locality cannot be null.");
        requireNonEmpty(locality.getPostalCode(), "Postal code cannot be null or empty.");
        requireNonEmpty(locality.getName(), "Locality name cannot be null or empty.");
        requireLengthBetween(locality.getPostalCode(), 4, 6,
                "Postal code must be between 4 and 6 characters.");
        requireLengthBetween(locality.getName(), 2, 60,
                "Locality name must be between 2 and 60 characters.");

        if (localityRepository.existsByPostalCode(locality.getPostalCode())) {
            throw new DuplicateFieldException("postalCode");
        }
        if (localityRepository.existsByName(locality.getName())) {
            throw new DuplicateFieldException("name");
        }
        localityRepository.save(locality);
    }

    @Transactional
    public void updateLocality(Long id, Locality locality) {
        requireNonNull(locality, "Locality cannot be null.");
        requireNonEmpty(locality.getPostalCode(),
                "Postal code cannot be null or empty.");
        requireNonEmpty(locality.getName(),
                "Locality name cannot be null or empty.");

        // Vérification de l'existence de la localité
        Locality existingLocality = localityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localité introuvable"));

        // Vérification des doublons de code postal
        if (localityRepository.existsByPostalCodeAndIdNot(locality.getPostalCode(), id)) {
            throw new DuplicateFieldException("postalCode");
        }
        // Vérification des doublons de nom
        if (localityRepository.existsByNameAndIdNot(locality.getName(), id)) {
            throw new DuplicateFieldException("name");
        }
        // Mise à jour des champs
        existingLocality.setPostalCode(locality.getPostalCode());
        existingLocality.setName(locality.getName());
        localityRepository.save(existingLocality);
    }

    @Transactional
    public void deleteLocality(Long id) {
        localityRepository.deleteById(id);
    }
}
