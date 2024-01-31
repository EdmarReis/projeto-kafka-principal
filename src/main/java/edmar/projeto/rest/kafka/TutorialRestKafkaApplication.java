package edmar.projeto.rest.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "edmar.projeto.rest.kafka")
public class TutorialRestKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialRestKafkaApplication.class, args);
	}

}
