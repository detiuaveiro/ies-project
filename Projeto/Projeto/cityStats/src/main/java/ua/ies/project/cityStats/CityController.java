package ua.ies.project.cityStats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private CityRepository repo;

    public CityController(CityRepository repo){
        this.repo = repo;
    }

    // Aggregate root

    @GetMapping("/cities")
    public List<City> all(){
        return repo.findAll();
    }

    @PostMapping("/cities")
    public City newCity(@RequestBody City newCity){
        return repo.save(newCity);
    }

    // Single item

    @GetMapping("/cities/{id}")
    public City one(@PathVariable Long id){
        return repo.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    @PutMapping("/cities/{id}")
    public City editCity(@RequestBody City newCity, @PathVariable Long id){
        return repo.findById(id).map(city ->
            {
                city.setCo(newCity.getCo());
                city.setCo2(newCity.getCo2());
                city.setSo2(newCity.getSo2());
                city.setNoise(newCity.getNoise());
                city.setRainPh(newCity.getRainPh());
                return repo.save(city);
            }).orElseGet(() -> // if not exists, creates new one
            {
                newCity.setId(id);
                return repo.save(newCity);
            }
        );
    }

    @DeleteMapping("/cities/{id}")
    public void deleteCity(@PathVariable Long id){
        repo.deleteById(id);
    }
}