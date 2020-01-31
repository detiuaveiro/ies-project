package ua.ies.project.cityStats;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {

    @Autowired
    private CityRepository repo;

    public CityController(CityRepository repo) {
        this.repo = repo;
    }

    // Aggregate root

    @GetMapping("/cities")
    public List<City> all(Model model) {
        model.addAttribute("cities", repo.findAll());
        return repo.findAll();
    }

    @PostMapping("/cities")
    public City newCity(@RequestBody City newCity, Model model) {
        model.addAttribute("cities", repo.findAll());
        return repo.save(newCity);
    }

    // Single item gets

    @GetMapping("/cities/{id}")
    public City findById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    @GetMapping("/cities/{name}/{district}")
    public City findByDistrictAndName(@PathVariable String district, @PathVariable String name) {
        List<City> city = repo.findByNameAndDistrict(name, district);
        return (city.size() == 1) ? city.get(0) : null;
    }

    @GetMapping("/cities/coords/{lat}/{lon}")
    public City findByLatAndLon(@PathVariable String lat, @PathVariable String lon) {
        List<City> city = repo.findByLatAndLon(Double.parseDouble(lat), Double.parseDouble(lon));
        return (city.size() == 1) ? city.get(0) : null;
    }

    //by each of the variables

    // other single item

    @PutMapping("/cities/{id}")
    public City editCity(@RequestBody City newCity, @PathVariable Long id) {
        return repo.findById(id).map(city ->
        {
            city.setCo(newCity.getCo());
            city.setCo2(newCity.getCo2());
            city.setSo2(newCity.getSo2());
            city.setNoise(newCity.getNoise());
            city.setRainPh(newCity.getRainPh());
            city.setO3(newCity.getO3());
            city.setNo2(newCity.getNo2());
            city.setTemperature(newCity.getTemperature());
            return repo.save(city);
        }).orElseGet(() -> // if not exists, creates new one
                {
                    newCity.setId(id);
                    return repo.save(newCity);
                }
        );
    }

    @DeleteMapping("/cities/{id}")
    public void deleteCity(@PathVariable Long id) {
        repo.deleteById(id);
    }
}