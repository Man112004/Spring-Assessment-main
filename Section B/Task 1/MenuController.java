
package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.model.MenuItem;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/menu")
public class MenuController {

    List<MenuItem> menu = new ArrayList<>();

    public MenuController() {
        menu.add(new MenuItem(1, "Pizza", 250));
        menu.add(new MenuItem(2, "Burger", 120));
        menu.add(new MenuItem(3, "Pasta", 180));
    }

    @GetMapping
    public List<MenuItem> getMenu() {
        return menu;
    }

    @GetMapping("/{id}")
    public MenuItem getMenuById(@PathVariable int id) {

        for (MenuItem m : menu) {
            if (m.getId() == id) {
                return m;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<MenuItem> addMenu(@RequestBody MenuItem m) {
        menu.add(m);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }
}
