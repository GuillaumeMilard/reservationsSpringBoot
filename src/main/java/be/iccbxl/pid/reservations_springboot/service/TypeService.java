package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.exception.DuplicateFieldException;
import be.iccbxl.pid.reservations_springboot.model.Type;
import be.iccbxl.pid.reservations_springboot.repository.TypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.iccbxl.pid.reservations_springboot.util.ValidationUtils.*;


@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add);// Conversion Iterable -> List
        return types;
    }

    public Optional<Type> getType(Long id) {
        return typeRepository.findById(id);
    }

    @Transactional
    public void addType(Type type) {

        // Vérification que le nom du type n'est pas null ou vide
        requireNonNull(type, "Type cannot be null.");
        requireNonEmpty(type.getName(),
                "Type name cannot be null or empty.");
        requireLengthBetween(type.getName(),
                2, 60, "Type name must be between 2 and 60 chars.");

        // Vérification des doublons de nom
        if (typeRepository.existsByName(type.getName())) {
            throw new DuplicateFieldException("name",
                    "Le type « " + type.getName() + " » existe déjà.");
        }
        typeRepository.save(type);
    }


    @Transactional
    public void updateType(Long id, Type type) {

        // Vérification que le nom du type n'est pas null
        requireNonNull(type, "Type cannot be null.");
        requireNonEmpty(type.getName(),
                "Type name cannot be null or empty.");

        // Vérifie l'existence pour éviter un insert involontaire
        typeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Type not found: " + id));

        // Vérification des doublons de nom
        if (typeRepository.existsByNameAndIdNot(type.getName(), id)) {
            throw new DuplicateFieldException("name",
                    "Le type « " + type.getName() + " » existe déjà.");
        }
        // Mise à jour du type
        type.setId(id);
        typeRepository.save(type);
    }

    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

}