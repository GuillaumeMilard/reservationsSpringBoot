package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Locality;
import be.iccbxl.pid.reservationsSpringBoot.repository.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalityService {

    @Autowired
    private LocalityRepository localityRepository;

    public List<Locality> getAllLocalities(){
        return (List<Locality>) localityRepository.findAll();
    }

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
