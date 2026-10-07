package com.hafizh.order.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hafizh.order.entity.Orders;
import com.hafizh.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Orders order) {
        try {
            return ResponseEntity.ok(orderService.buatOrder(order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Orders> getAll(@RequestParam(value = "pelangganId", required = false) Long pelangganId) {
        if (pelangganId != null) {
            return orderService.getByPelanggan(pelangganId);
        }
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Orders order = orderService.getById(id);
        if (order == null) {
            return ResponseEntity.status(404).body("Order tidak ditemukan");
        }
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Orders order) {
        try {
            Orders hasil = orderService.updateOrder(id, order);
            if (hasil == null) {
                return ResponseEntity.status(404).body("Order tidak ditemukan");
            }
            return ResponseEntity.ok(hasil);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (!orderService.deleteOrder(id)) {
            return ResponseEntity.status(404).body("Order tidak ditemukan");
        }
        return ResponseEntity.noContent().build();
    }
}