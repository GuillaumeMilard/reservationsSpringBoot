package be.iccbxl.pid.reservationsSpringBoot.repository;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository <Artist, Long> {
    List<Artist> findByLastname(String lastname);
    Artist findById(long id);
    Page<Artist> findByLastnameContains(String keyword, Pageable pageable);


}
