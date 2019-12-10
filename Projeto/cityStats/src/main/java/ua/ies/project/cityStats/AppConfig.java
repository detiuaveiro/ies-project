package ua.ies.project.cityStats;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Calendar;
import java.util.Date;

@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    private CityRepository repo;

    //test
    @Bean
    public Stat stat(){
        Date today = Calendar.getInstance().getTime();
        City city = new City("Guarda", "Guarda", 40.0, 40.0);
        //return repo.save(new Stat(today, city));
        return null;
    }

    @Bean
    public Queue queue(){
        return new Queue("cities");
    }

    // @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1(){
            return new Receiver(1);
        }

        /*
        * @Bean
        public Receiver receiver2(){
            return new Receiver(2);
        }*/
    }
}