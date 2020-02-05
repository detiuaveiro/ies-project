package ua.ies.project.cityStats;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.ies.project.cityStats.repository.CityRepository;
import ua.ies.project.cityStats.repository.StatsRepository;

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