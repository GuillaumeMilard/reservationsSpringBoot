package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.Type;
import be.iccbxl.pid.reservations_springboot.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add);// Conversion Iterable -> List
        return types;
    }

    public Optional<Type> getType(Long id) {
        return typeRepository.findById(id);
    }

    public void addType(Type type) {
        typeRepository.save(type);
    }

    public void updateType(Long id, Type type) {
       typeRepository.save(type);
    }

    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

}