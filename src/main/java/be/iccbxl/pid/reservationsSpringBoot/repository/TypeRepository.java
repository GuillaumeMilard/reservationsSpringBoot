package be.iccbxl.pid.reservationsSpringBoot.repository;

import be.iccbxl.pid.reservationsSpringBoot.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeRepository extends CrudRepository<Type, Long>  {
    List<Type> findByTypename(String typename);

    Type findById(long id);

}
