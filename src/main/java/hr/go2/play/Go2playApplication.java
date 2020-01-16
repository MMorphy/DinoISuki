package hr.go2.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.content.commons.repository.Store;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("hr.go2.play.repositories")
public class Go2playApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Go2playApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Go2playApplication.class);
    }
	
	//GET na /12345/<imeFilea>
	@StoreRestResource(path = "12345")
	public interface VideoStore extends Store<String>{}

}
