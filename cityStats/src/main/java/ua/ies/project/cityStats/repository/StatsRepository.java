package ua.ies.project.cityStats.repository;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ies.project.cityStats.model.City;
import ua.ies.project.cityStats.model.Stat;

public interface StatsRepository extends JpaRepository<Stat, Long> {
    List<Stat> findByDay(Date day);

    Stat findByDayAndCity(Date day, City city);

    List<Stat> findAllByDay(Date day);
    List <Stat> findByCity(City city);

    List<Stat> findAllByDayAndCity(Date day, City city);
}
