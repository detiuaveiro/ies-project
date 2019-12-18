package ua.ies.project.cityStats;

public class StatNotFoundException extends RuntimeException {
    public StatNotFoundException(Long id){
        super("Could not find stat " + id);
    }
}
