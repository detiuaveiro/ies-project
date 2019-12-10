package ua.ies.project.cityStats;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
public class Year {

    private double avgCo;
    private double avgCo2;
    private double avgSo2;
    private double avgNo2;
    private double avgO3;
    private double avgNoise;
    private double avgRainPh;
    private double avgTemperature;

}
