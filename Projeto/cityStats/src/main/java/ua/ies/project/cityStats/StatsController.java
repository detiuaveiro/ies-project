package ua.ies.project.cityStats;

<<<<<<<HEAD
        =======
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
>>>>>>>branch
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

    // Single Item

    @GetMapping("/stats/{id}")
    private Stat getByid(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new StatNotFoundException(id));
    }

    // Single Item

    @GetMapping("/stats/{id}")
    private Stat getByid(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new StatNotFoundException(id));
    }

    @GetMapping("/stats/day/{day}")
    private List<Stat> getByDate(@PathVariable String day) throws ParseException {
        List<Stat> stats = repo.findAllByDay(new SimpleDateFormat("yyyy-MM-dd").parse(day));
        return stats;
    }

    //by each of the variables

    @DeleteMapping("/stats/{id}")
    private void deleteStat(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
