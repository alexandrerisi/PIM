package nl.zamro.pim;

import nl.zamro.pim.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PimApplication {

    public static void main(String[] args) {
        SpringApplication.run(PimApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ProductRepository repository) {
        return args -> {
            //repository.findAll().forEach(System.out::println);
        };
    }
}
