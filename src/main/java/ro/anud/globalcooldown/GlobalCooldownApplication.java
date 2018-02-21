package ro.anud.globalcooldown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlobalCooldownApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalCooldownApplication.class, args);
	}
}
