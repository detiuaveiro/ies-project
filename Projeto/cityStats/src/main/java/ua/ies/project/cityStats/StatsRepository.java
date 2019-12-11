package ua.ies.project.cityStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface StatsRepository extends JpaRepository<Stat, Long> {
    List<Stat> findByDay(Date day);
    List<Stat> findByDayAndCity(Date day, City city);
}
