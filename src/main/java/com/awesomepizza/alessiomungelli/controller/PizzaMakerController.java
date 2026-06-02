package com.awesomepizza.alessiomungelli.controller;

import com.awesomepizza.alessiomungelli.dto.OrderResponseDTO;
import com.awesomepizza.alessiomungelli.service.OrderService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pizzaiolo")
public class PizzaMakerController {

    private final OrderService orderService;

    public PizzaMakerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("/next")
    public OrderResponseDTO takeNext() {
        return orderService.takeNextOrder();
    }

    @PatchMapping("/{id}/complete")
    public OrderResponseDTO complete(@PathVariable Long id) {
        return orderService.completeOrder(id);
    }
}
