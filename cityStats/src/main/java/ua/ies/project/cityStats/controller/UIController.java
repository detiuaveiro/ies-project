package ua.ies.project.cityStats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.ies.project.cityStats.repository.CityRepository;
import ua.ies.project.cityStats.repository.StatsRepository;
import ua.ies.project.cityStats.exception.StatNotFoundException;
import ua.ies.project.cityStats.model.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class UIController {

    @Autowired
    private CityRepository cities;

    @Autowired
    private StatsRepository stats;

    public UIController(CityRepository cities, StatsRepository stats){
        this.cities = cities;
        this.stats = stats;
    }

    @GetMapping("/")
    public String all(Model model){
        List<City> citiesList = cities.findAll();
        model.addAttribute("cities", citiesList);
        return "index.html";
    }

    @GetMapping("/{id}")
    public String getCityStats(Model model, @PathVariable Long id){
        City city = cities.findById(id).orElseThrow(() -> new StatNotFoundException(id));
        model.addAttribute("city", city);
        model.addAttribute("stats", stats.findByCity(city));
        return "city.html";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about.html";
    }

    private List<String> getAllDistricts(List<City> cities){
        List<String> districts = new ArrayList<>();
        for (City city: cities) {
            String district = city.getDistrict();
            if (!districts.contains(district))
                districts.add(district);
        }
        return districts;
    }

    //get all cities divided by district
    private HashMap<String, List<City>> getAllCitiesMapped(List<City> cities){
        HashMap<String, List<City>> map = new HashMap<>();
        for (City city : cities){
            String district = city.getDistrict();
            if (!map.containsKey(district))
                map.put(district, new ArrayList<City>());
            map.get(district).add(city);
        }
        return map;
    }
}
