package be.iccbxl.pid.reservations_springboot.repository;

import be.iccbxl.pid.reservations_springboot.model.Locality;
import org.springframework.data.repository.CrudRepository;

public interface LocalityRepository extends CrudRepository<Locality, Long> {

    Locality findByPostalCode(String postalCode);

    Locality findByName(String name);

    // Vérifie si une localité avec le même code postal existe
    Boolean existsByPostalCode(String postalCode);

    // Vérifie si une localité avec le même nom existe
    Boolean existsByName(String name);

    // Vérifie si une localité avec le même code postal existe, mais pas celle avec l'ID spécifié
    Boolean existsByPostalCodeAndIdNot(String postalCode, Long id);

    // Vérifie si une localité avec le même nom existe, mais pas celle avec l'ID spécifié
    Boolean existsByNameAndIdNot(String name, Long id);

}

