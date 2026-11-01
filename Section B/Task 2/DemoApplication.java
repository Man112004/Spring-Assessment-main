
package com.example;

import com.example.model.Order;
import com.example.repository.OrderRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(OrderRepository repo) {
        return args -> {

            repo.save(new Order("Man", "Pizza", 2, 500));
            repo.save(new Order("Rahul", "Burger", 1, 120));

            System.out.println("All Orders:");

            for (Order o : repo.findAll()) {
                System.out.println(
                        o.getId() + " " +
                                o.getCustomerName() + " " +
                                o.getItemName() + " " +
                                o.getQuantity() + " " +
                                o.getTotalAmount());
            }

            System.out.println("Orders of Man:");

            for (Order o : repo.findByCustomerName("Man")) {
                System.out.println(
                        o.getId() + " " +
                                o.getCustomerName() + " " +
                                o.getItemName() + " " +
                                o.getQuantity() + " " +
                                o.getTotalAmount());
            }
        };
    }
}
