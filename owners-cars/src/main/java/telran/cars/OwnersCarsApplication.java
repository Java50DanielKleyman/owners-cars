package telran.cars;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OwnersCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnersCarsApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runQueries(JPQLQueryConsole console) {
//		return args -> {
//			console.run(); // Run the console interactively
//		};
//	}

}