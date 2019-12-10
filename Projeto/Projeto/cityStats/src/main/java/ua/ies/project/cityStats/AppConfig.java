package ua.ies.project.cityStats;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class AppConfig {

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