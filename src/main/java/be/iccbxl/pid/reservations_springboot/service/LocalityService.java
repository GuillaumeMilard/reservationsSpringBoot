package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.Locality;
import be.iccbxl.pid.reservations_springboot.repository.LocalityRepository;
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
        localityRepository.save(locality);
    }

    public void updateLocality(Long id, Locality locality) {
       localityRepository.save(locality);
    }

    public void deleteLocality(Long id) {
        localityRepository.deleteById(id);
    }

}
