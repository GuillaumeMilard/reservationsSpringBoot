package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Locality;
import be.iccbxl.pid.reservationsSpringBoot.repository.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalityService {

    @Autowired
    private LocalityRepository localityRepository;

    public Locality getLocality(long id) {
        return localityRepository.findById(id);
    }

    public void addLocality(Locality locality) {
        localityRepository.save(locality);
    }

    public void updateLocality(long id, Locality locality) {
        localityRepository.save(locality);
    }

    public void deleteLocality(long id) {
        localityRepository.deleteById(id);
    }
}
