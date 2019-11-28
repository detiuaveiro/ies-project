package ua.ies.project.cityStats;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Long id){
        super("Could not find city " + id);
    }
}