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

    /*
    * //test
    @Bean
    public Stat stat(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY , 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();
        City city = new City("Guarda", "Guarda", 40.0, 40.0);
        city.setId(14L);
        cities.save(city);
        Stat stat;
        List<Stat> list = stats.findByDayAndCity(today, city);
        if (!list.isEmpty())
            stat = list.get(0);
        else
            stat = new Stat(today, city);
        return stats.save(stat);
    }
    * */

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