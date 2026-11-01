
package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Order;
import com.example.model.RecommendRequest;
import com.example.repository.OrderRepository;
import com.example.service.AIService;

import jakarta.validation.Valid;

@RestController
public class FoodController {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private AIService aiService;

    @GetMapping("/menu")
    public List<String> getMenu() {

        return List.of(
                "Pizza",
                "Burger",
                "Pasta",
                "Sandwich",
                "Dosa");
    }

    @PostMapping("/orders")
    public Order placeOrder(@Valid @RequestBody Order order) {

        return repo.save(order);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {

        return repo.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/recommend")
    public Map<String, String> recommend(
            @Valid @RequestBody RecommendRequest request) throws Exception {

        String suggestion = aiService.getRecommendation(
                request.getPreference());

        return Map.of("suggestion", suggestion);
    }

    @GetMapping("/admin/orders")
    public List<Order> getAllOrders() {

        return repo.findAll();
    }

    @DeleteMapping("/admin/orders/{id}")
    public String deleteOrder(@PathVariable int id) {

        repo.deleteById(id);

        return "Order deleted successfully";
    }
}
