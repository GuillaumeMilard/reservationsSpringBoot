package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.dto.LocationCreateDTO;
import be.iccbxl.pid.reservations_springboot.model.Locality;
import be.iccbxl.pid.reservations_springboot.model.Location;
import be.iccbxl.pid.reservations_springboot.repository.LocalityRepository;
import be.iccbxl.pid.reservations_springboot.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocalityRepository localityRepository;


    public LocationService(LocationRepository locationRepository,
                           LocalityRepository localityRepository) {
        this.locationRepository = locationRepository;
        this.localityRepository = localityRepository;
    }
    // Méthode pour créer une nouvelle location à partir d'un DTO
    public Location createLocation(LocationCreateDTO dto) {
        Locality locality = localityRepository.findById(dto.getLocalityId())
                .orElseThrow(() -> new EntityNotFoundException("Locality introuvable avec id : " + dto.getLocalityId()));

        Location location = new Location(
                null,
                dto.getDesignation(),
                dto.getAddress(),
                locality,
                dto.getWebsite(),
                dto.getPhone()
        );
        return locationRepository.save(location);
    }

    // Méthode pour récupérer toutes les locations
    public List<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        locationRepository.findAll().forEach(locations::add);
        return locations;
    }

    // Méthode pour récupérer une location par son ID
    public Location getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location introuvable avec id : " + id));
    }

    // Méthode pour mettre à jour une location (lieu) existante
    public Location updateLocation(Long id, LocationCreateDTO dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location introuvable avec id : " + id));
        Locality locality = localityRepository.findById(dto.getLocalityId())
                .orElseThrow(() -> new EntityNotFoundException("Locality introuvable avec id : " + dto.getLocalityId()));
        location.setDesignation(dto.getDesignation());
        location.setAddress(dto.getAddress());
        location.setWebsite(dto.getWebsite());
        location.setPhone(dto.getPhone());
        location.setLocality(locality);
        return locationRepository.save(location);
    }

    // Méthode pour supprimer une location par son ID
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location introuvable avec id : " + id);
        }
        locationRepository.deleteById(id);
    }
}
