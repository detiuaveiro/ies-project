package ua.ies.project.cityStats;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(exclude={"day", "city"})
@Table(name="stats")
public class Stat implements Serializable {

    @Column(name = "startTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @Id
    private Date day;

    @ManyToOne
    @JoinColumn(name = "id")
    @Id
    private City city;

    public String json() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e){
            return "{}";
        }
    }
}
