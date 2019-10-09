package hr.go2.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("hr.go2.play.repositories")
public class Go2playApplication {

	public static void main(String[] args) {
		SpringApplication.run(Go2playApplication.class, args);
	}

}
