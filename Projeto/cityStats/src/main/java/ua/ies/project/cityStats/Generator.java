package ua.ies.project.cityStats;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Deprecated
public class Generator {

    private ArrayList<City> cities;

    public Generator(){
        cities = new ArrayList<>();
        cities.add(new City("Aveiro", "Aveiro", 40.6412, -8.65362));
        cities.add(new City("Arouca", "Aveiro", 40.9289, -8.24364));
        cities.add(new City("Sintra", "Lisboa", 38.7984, -9.38811));
        for (int i = 0; i < cities.size(); i++){
            cities.get(i).setId((long) (i + 1));
        }
    }

    private void generate(){
        Random random = new Random();
        for (City city : cities){
            city.setCo(random.nextInt(1200)); //ppbv
            city.setCo2(random.nextInt(38) + 382); //ppm
            city.setSo2(random.nextInt(30) + 5); //microg/m^3
            city.setNoise(random.nextInt(30) + 15); //db
            city.setRainPh(random.nextInt(7));
        }
    }

    public void clock() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //create connections to localhost
        try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()){
            while (true) {
                generate();  //generate data for each city
                for (City city : cities){
                    String QUEUE_NAME = city.getId() + "";
                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    String message = city.json();
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                    System.out.println(" [generator] sent '" + message + "'");
                }
                Thread.sleep(60000);
            }
        }
    }
}
