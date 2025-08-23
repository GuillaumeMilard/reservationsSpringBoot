package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.Location;
import be.iccbxl.pid.reservations_springboot.model.Show;
import be.iccbxl.pid.reservations_springboot.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }


    public List<Show> getAll() {
        List<Show> shows = new ArrayList<>();
        showRepository.findAll().forEach(shows::add);
        return shows;
    }

    public Optional<Show> getShow(Long id) {
        return showRepository.findById(id);
    }

    public void addShow(Show show) {
        showRepository.save(show);
    }

    public void updateShow(String id, Show show) {
        showRepository.save(show);
    }

    public void deleteShow(String id) {
        Long indice = (long) Integer.parseInt(id);
        showRepository.deleteById(indice);
    }

    public List<Show> getFromLocation(Location location) {
        return showRepository.findByLocation(location);
    }
}
