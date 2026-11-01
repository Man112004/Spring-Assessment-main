
package com.example.repository;

import java.util.List;

import com.example.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByCustomerName(String name);

}
