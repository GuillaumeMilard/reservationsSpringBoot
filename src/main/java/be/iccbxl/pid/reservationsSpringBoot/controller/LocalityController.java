package be.iccbxl.pid.reservationsSpringBoot.controller;

import be.iccbxl.pid.reservationsSpringBoot.model.Locality;
import be.iccbxl.pid.reservationsSpringBoot.service.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LocalityController {
    @Autowired
    LocalityService localityService ;
    @GetMapping("/localities")
    public String index(Model model){
        List<Locality> localitiesList = localityService.getAllLocalities();
        model.addAttribute("localities", localitiesList );
        return "locality/index";
    }

}
