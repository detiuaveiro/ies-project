package ua.ies.project.cityStats;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude="id")
@Table(name="stats", uniqueConstraints={@UniqueConstraint(columnNames = {"day" , "city"})})
public class Stat implements Serializable {

    @GeneratedValue
    private @Id Long id;

    @Column(name = "day", columnDefinition="DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date day;

    @ManyToOne
    @JoinColumn(name = "city")
    @NotNull
    private City city;

    public Stat(Date day, City city){
        this.day = day;
        this.city = city;
        this.co = 0;
        this.co2 = 0;
        this.no2 = 0;
        this.o3 = 0;
        this.noise = 0;
        this.rainPh = 0;
        this.temperature = 0;
        this.nUpdates = 0;
    }
    //Daily avgs:
    private double co;
    private double co2;
    private double so2;
    private double no2;
    private double o3;
    private double noise;
    private double rainPh;
    private double temperature;

    private int nUpdates;

    public void updateStat(Stat stat){
        this.co = (this.co * this.nUpdates + stat.getCity().getCo()) / (this.nUpdates + 1);
        this.co2 = (this.co2 * this.nUpdates + stat.getCity().getCo2()) / (this.nUpdates + 1);
        this.so2 = (this.so2 * this.nUpdates + stat.getCity().getSo2()) / (this.nUpdates + 1);
        this.no2 = (this.no2 * this.nUpdates + stat.getCity().getNo2()) / (this.nUpdates + 1);
        this.o3 = (this.o3 * this.nUpdates + stat.getCity().getO3()) / (this.nUpdates + 1);
        this.noise = (this.noise * this.nUpdates + stat.getCity().getNoise()) / (this.nUpdates + 1);
        this.rainPh = (this.rainPh * this.nUpdates + stat.getCity().getRainPh()) / (this.nUpdates + 1);
        this.temperature = (this.temperature * this.nUpdates + stat.getCity().getTemperature()) / (this.nUpdates + 1);
        // increment nUpdates:
        this.nUpdates++;
    }

    public String json() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e){
            return "{}";
        }
    }
}
