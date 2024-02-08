package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service //indiquer qu’il s’agit d’une classe service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public Page<Artist> getAllArtists(int page, int size, String kw) {
        // List<Artist> artists = new ArrayList<>();
        // artistRepository.findAll().forEach(artists::add);
        //return artists;
        return (Page<Artist>) artistRepository.findByLastnameContains(kw, PageRequest.of(page, size));
    }

    public Artist getArtist(long id) {
        return artistRepository.findById(id);
    }

    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void updateArtist(long id, Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(long id) {
        artistRepository.deleteById(id);
    }

    public List<Artist> getAllArtists() {
       List<Artist> artists = new ArrayList<>();
       artistRepository.findAll().forEach(artists::add);
       return artists;
    }

}
