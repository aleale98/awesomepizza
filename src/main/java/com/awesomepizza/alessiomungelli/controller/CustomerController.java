package com.awesomepizza.alessiomungelli.controller;

import com.awesomepizza.alessiomungelli.dto.OrderResponseDTO;
import com.awesomepizza.alessiomungelli.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class CustomerController {

    private final OrderService orderService;

    public CustomerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Restituisce 201 invece di 200
    public OrderResponseDTO createOrder() {
        return orderService.createOrder();
    }

    // check status
    @GetMapping("/{code}")
    public OrderResponseDTO getOrder(@PathVariable String code) {
        return orderService.getOrderByCode(code);
    }
}
