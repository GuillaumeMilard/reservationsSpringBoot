package be.iccbxl.pid.reservations_springboot.controller;

import be.iccbxl.pid.reservations_springboot.dto.LocationCreateDTO;
import be.iccbxl.pid.reservations_springboot.model.Location;
import be.iccbxl.pid.reservations_springboot.service.LocalityService;
import be.iccbxl.pid.reservations_springboot.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocalityService localityService;

    public LocationController(LocationService locationService,
                              LocalityService localityService) {
        this.locationService = locationService;
        this.localityService = localityService;
    }


    @GetMapping
    public String index(Model model) {
        List<Location> locations = locationService.getAll(); // service transforme Iterable en List
        model.addAttribute("locations", locations);
        return "location/index"; // templates/location/index.html
    }


    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Location location = locationService.getLocation(id);
        model.addAttribute("location", location);
        return "location/show"; // templates/location/show.html
    }


    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("location", new LocationCreateDTO());
        model.addAttribute("localities", localityService.getAll()); // Iterable suffisant pour Thymeleaf
        return "location/create"; // templates/location/create.html
    }


    @PostMapping
    public String create(@Valid @ModelAttribute("location") LocationCreateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("localities", localityService.getAll());
            return "location/create";
        }
        locationService.createLocation(dto);
        return "redirect:/locations";
    }

    // UPDATE - formulaire
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Location location = locationService.getLocation(id);
        LocationCreateDTO dto = new LocationCreateDTO();
        dto.setDesignation(location.getDesignation());
        dto.setAddress(location.getAddress());
        dto.setWebsite(location.getWebsite());
        dto.setPhone(location.getPhone());
        dto.setLocalityId(location.getLocality().getId());

        model.addAttribute("location", dto);
        model.addAttribute("localities", localityService.getAll());
        model.addAttribute("id", id);

        return "location/edit"; // templates/location/edit.html
    }

    // UPDATE - traitement
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("location") LocationCreateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("localities", localityService.getAll());
            return "location/edit";
        }
        locationService.updateLocation(id, dto);
        return "redirect:/locations";
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return "redirect:/locations";
    }

}