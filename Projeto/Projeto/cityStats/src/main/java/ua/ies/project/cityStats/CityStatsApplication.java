package ua.ies.project.cityStats;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.ConnectionFactory;

@SpringBootApplication
public class CityStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityStatsApplication.class, args);
	}
}
