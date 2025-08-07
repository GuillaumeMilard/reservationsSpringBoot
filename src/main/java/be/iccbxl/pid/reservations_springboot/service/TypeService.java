package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.Type;
import be.iccbxl.pid.reservations_springboot.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add);// Conversion Iterable -> List
        return types;
    }

    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    public void addType(Type type) {
        typeRepository.save(type);
    }

    public void updateType(Long id, Type type) {
        if (typeRepository.existsById(id)) {
            type.setId(id); // s'assurer que l'ID est bien celui passé en paramètre
            typeRepository.save(type);
        }
    }

    public void deleteType(Long id) {
        if (typeRepository.existsById(id)) {
            typeRepository.deleteById(id);
        }
    }

}