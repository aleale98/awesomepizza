package com.awesomepizza.alessiomungelli.service;

import com.awesomepizza.alessiomungelli.domain.PizzaOrder;
import com.awesomepizza.alessiomungelli.domain.Status;
import com.awesomepizza.alessiomungelli.dto.OrderResponseDTO;
import com.awesomepizza.alessiomungelli.persistence.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder() {
        PizzaOrder order = new PizzaOrder();
        String customerCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderCode(customerCode);
        order.setStatus(Status.PLACED);

        // 2. Salvo l'ENTITY
        PizzaOrder savedOrder = orderRepository.save(order);

        // 3. Trasformo l'ENTITY salvata in DTO per il controller
        return OrderResponseDTO.fromEntity(savedOrder);
    }

    @Transactional
    public OrderResponseDTO takeNextOrder() {
        if (orderRepository.existsByStatus(Status.IN_PROGRESS)) {
            throw new RuntimeException("Pizza maker busy");
        }

        // Recupero l'ENTITY dal database
        PizzaOrder nextOrder = orderRepository.findFirstByStatusOrderByCreatedAtAsc(Status.PLACED)
                .orElseThrow(() -> new RuntimeException("Empty queue!"));

        // Modifico l'ENTITY
        nextOrder.setStatus(Status.IN_PROGRESS);
        PizzaOrder savedOrder = orderRepository.save(nextOrder);

        // Restituisco il DTO
        return OrderResponseDTO.fromEntity(savedOrder);
    }

    @Transactional
    public OrderResponseDTO completeOrder(Long id) {
        // Cerco l'ENTITY per ID
        PizzaOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wrong id!"));

        if (order.getStatus() != Status.IN_PROGRESS) {
            throw new RuntimeException("Order is not in progress");
        }

        order.setStatus(Status.COMPLETED);
        PizzaOrder savedOrder = orderRepository.save(order);

        return OrderResponseDTO.fromEntity(savedOrder);
    }

    @Transactional
    public OrderResponseDTO getOrderByCode(String code) {
        // Cerco l'ENTITY per codice
        PizzaOrder order = orderRepository.findByOrderCode(code)
                .orElseThrow(() -> new RuntimeException("Order not found with code: " + code));

        return OrderResponseDTO.fromEntity(order);
    }
}