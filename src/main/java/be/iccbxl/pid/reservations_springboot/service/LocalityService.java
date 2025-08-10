package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.exception.DuplicateFieldException;
import be.iccbxl.pid.reservations_springboot.model.Locality;
import be.iccbxl.pid.reservations_springboot.repository.LocalityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocalityService {

    @Autowired
    private LocalityRepository localityRepository;

    public List<Locality> getAll() {
        List<Locality> localities = new ArrayList<>();
        localityRepository.findAll().forEach(localities::add); // Conversion Iterable -> List
        return localities;
    }

    public Optional<Locality> getLocality(Long id) {
        return localityRepository.findById(id);
    }

    public void addLocality(Locality locality) {
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

//    public void updateLocality(Long id, Locality locality) {
//       localityRepository.save(locality);
//    }


    public void deleteLocality(Long id) {
        localityRepository.deleteById(id);
    }
}
