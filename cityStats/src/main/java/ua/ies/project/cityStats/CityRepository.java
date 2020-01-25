package ua.ies.project.cityStats;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameAndDistrict(String name, String district);
    List<City> findByDistrict(String district);
    List<City> findByLatAndLon(double lat, double lon);
}