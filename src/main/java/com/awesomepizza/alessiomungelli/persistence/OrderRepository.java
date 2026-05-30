package com.awesomepizza.alessiomungelli.persistence;

import com.awesomepizza.alessiomungelli.domain.PizzaOrder;
import com.awesomepizza.alessiomungelli.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<PizzaOrder, Long> {
    // For the final customer to check the order status
    Optional<PizzaOrder> findByOrderCode(String orderCode);

    // For the pizza maker to manage the FIFO queue
    Optional<PizzaOrder> findFirstByStatusOrderByCreatedAtAsc(Status status);

    // To check if the pizza maker is busy
    boolean existsByStatus(Status status);
}
