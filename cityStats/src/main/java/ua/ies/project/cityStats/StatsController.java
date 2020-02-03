package ua.ies.project.cityStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class StatsController {

    @Autowired
    private StatsRepository repo;

    @Autowired
    private CityRepository cityRepo;

    public StatsController(StatsRepository repo) {
        this.repo = repo;
    }

    //Aggregate root

    @GetMapping("/stats")
    private List<Stat> all() {
        return repo.findAll();
    }

    @PostMapping("/stats")
    private Stat newStat(@RequestBody Stat newStat) {
        return repo.save(newStat);
    }

//    @PutMapping("/stats")
//    private Stat updateStat(@RequestBody Stat updated) {
//
//    }

    @GetMapping("/stats/{id}")
    private Stat getByid(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new StatNotFoundException(id));
    }

    @GetMapping("/stats/day/{day}")
    private List<Stat> getByDate(@PathVariable String day) throws ParseException {
        return repo.findByDay(new SimpleDateFormat("yyyy-MM-dd").parse(day));
    }

    @GetMapping("/stats/city/{id}")
    private List<Stat> getByCity(@PathVariable Long id) {
        City city = new City();
        city.setId(id);
        return repo.findByCity(city);
    }

    @GetMapping("/stats/day/{date}/city/{cityid}")
    private Stat getByDateAndCity(@PathVariable String date,
                                  @PathVariable Long cityid) throws  ParseException {
        Date day = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        City city = new City();
        city.setId(cityid);
        return repo.findByDayAndCity(day, city);
    }

    @GetMapping("/stats/city/{name}/district/{district}")
    private List<Stat> findByNameAndDistrict(@PathVariable String name,
                                             @PathVariable String district){
        List<City> cities = cityRepo.findByNameAndDistrict(name, district);
        if (cities.size() != 1 ) return null;
        City city = cities.get(0);
        return repo.findByCity(city);
    }

    @GetMapping("/stats/city/{name}/district/{district}/{date}")
    private Stat findByNameAndDistrictAndDate(@PathVariable String name,
                                             @PathVariable String district, @PathVariable String date)
            throws ParseException {
        List<City> cities = cityRepo.findByNameAndDistrict(name, district);
        if (cities.size() != 1 ) return null;
        City city = cities.get(0);
        Date day = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return repo.findByDayAndCity(day, city);
    }

    //by each of the variables

    @DeleteMapping("/stats/{id}")
    private void deleteStat(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
