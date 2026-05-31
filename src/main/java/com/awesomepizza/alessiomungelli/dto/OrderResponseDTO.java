package com.awesomepizza.alessiomungelli.dto;

import com.awesomepizza.alessiomungelli.domain.PizzaOrder;
import com.awesomepizza.alessiomungelli.domain.Status;
import java.time.LocalDateTime;

public record OrderResponseDTO(
        Long id,
        String orderCode,
        Status status,
        LocalDateTime createdAt
) {
    public static OrderResponseDTO fromEntity(PizzaOrder order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getOrderCode(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}