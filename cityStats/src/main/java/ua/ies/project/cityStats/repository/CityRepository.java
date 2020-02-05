package ua.ies.project.cityStats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ies.project.cityStats.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameAndDistrict(String name, String district);
    List<City> findByLatAndLon(double lat, double lon);
}