package ua.ies.project.cityStats;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

@Slf4j
public class Receiver implements CommandLineRunner {

    private final CityRepository repo;

    public Receiver(CityRepository repo){
        this.repo = repo;
    }

    @Override
    public void run (String... args){
        log.info("RECEIVING........................................");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println(repo.findAll().size());
            while(true)
                for (City city: repo.findAll()){
                    String queueName = city.getId() + "";
                    channel.queueDeclare(queueName, false,
                            false, false, null);

                    DeliverCallback deliverCallBack = (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), "UTF-8");
                        log.info("[server] received '" + message + "'");
                    };
                    channel.basicConsume(queueName, deliverCallBack, consumerTag -> {});
                }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}