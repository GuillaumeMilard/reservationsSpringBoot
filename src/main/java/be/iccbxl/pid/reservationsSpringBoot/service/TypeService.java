package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Type;
import be.iccbxl.pid.reservationsSpringBoot.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public Type getType(long id) {
        return typeRepository.findById(id);
    }

    public void addType(Type type) {
        typeRepository.save(type);
    }

    public void updateType(long id, Type type) {
        typeRepository.save(type);
    }

    public void deleteType(long id) { typeRepository.deleteById(id); }

}
