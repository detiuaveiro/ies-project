package ua.ies.project.cityStats;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(exclude={"name", "district", "lat", "lon"})
@Data
public class City {

    private @Id @GeneratedValue Long id;

    private String name;
    private String district;

    private double lat;
    private double lon;
    private double co;
    private double co2;
    private double so2;
    private double noise;
    private double rainPh;

    public City(String name, String district, double lat, double lon){
        this.name = name;
        this.district = district;
        this.lat = lat;
        this.lon = lon;
    }

    public String json() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e){
            return "{}";
        }
    }
}