package ua.ies.project.cityStats;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    private StatsRepository stats;

    @Autowired
    private CityRepository cities;

    @Bean
    public Queue queue(){
        return new Queue("cities");
    }

    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1(){
            return new Receiver(1);
        }
    }
}