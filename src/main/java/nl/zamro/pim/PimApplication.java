package nl.zamro.pim;

import nl.zamro.pim.repository.ProductRepository;
import nl.zamro.pim.service.CategoryService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

@SpringBootApplication
public class PimApplication {

    public static void main(String[] args) {
        SpringApplication.run(PimApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner runner(ProductRepository productRepository, CategoryService categoryService) {
        return args -> {
            System.out.println("Number of products before removing category -> " + ((Collection<?>) productRepository.findAll()).size());
            categoryService.removeCategoryById(48622);
            System.out.println("Number of products after removing category -> " + ((Collection<?>) productRepository.findAll()).size());
        };
    }*/
}
