package be.iccbxl.pid.reservationsSpringBoot.repository;

import be.iccbxl.pid.reservationsSpringBoot.model.Locality;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocalityRepository extends CrudRepository<Locality,Long> {

    List<Locality> findAll();

    List<Locality> findByLocalityname(String localityname);

    Locality findById(long id);

}
