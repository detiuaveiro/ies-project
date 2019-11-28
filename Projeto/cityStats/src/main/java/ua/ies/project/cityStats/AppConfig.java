package ua.ies.project.cityStats;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class AppConfig {

    /**@Bean
    CommandLineRunner initDatabase(CityRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new City("Aveiro", "Aveiro", 40.6412, -8.65362)));
            log.info("Preloading " + repository.save(new City("Arouca", "Aveiro", 40.9289, -8.24364)));
            log.info("Preloading " + repository.save(new City("Sintra", "Lisboa", 38.7984, -9.38811)));
        };
    }**/

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

        @Bean
        public Receiver receiver2(){
            return new Receiver(2);
        }
    }
}