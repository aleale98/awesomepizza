package com.awesomepizza.alessiomungelli;

import com.awesomepizza.alessiomungelli.domain.PizzaOrder;
import com.awesomepizza.alessiomungelli.domain.Status;
import com.awesomepizza.alessiomungelli.dto.OrderResponseDTO;
import com.awesomepizza.alessiomungelli.persistence.OrderRepository;
import com.awesomepizza.alessiomungelli.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Should create a new order with PLACED status and order code length 8")
    public void createNewOrder(){
        when(orderRepository.save(any(PizzaOrder.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        OrderResponseDTO resultOrder = orderService.createOrder();
        assertThat(resultOrder).isNotNull();
        assertThat(resultOrder.status()).isEqualTo(Status.PLACED);
        assertThat(resultOrder.orderCode()).hasSize(8);
    }

    @Test
    @DisplayName("Try to take new order, pizza maker is not busy and throws no exception")
    public void whenPizzaioloIsFree_thenTakeNextOrder() {
        PizzaOrder mockOrder = new PizzaOrder();
        mockOrder.setStatus(Status.PLACED);
        when(orderRepository.existsByStatus(Status.IN_PROGRESS)).thenReturn(false);
        when(orderRepository.findFirstByStatusOrderByCreatedAtAsc(Status.PLACED))
                .thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any(PizzaOrder.class))).thenAnswer(i -> i.getArguments()[0]);
        OrderResponseDTO result = orderService.takeNextOrder();
        assertThat(result.status()).isEqualTo(Status.IN_PROGRESS);
        verify(orderRepository).save(mockOrder);
    }

    @Test
    @DisplayName("Try to take new order but pizza maker is busy and throws exception")
    void whenPizzaioloIsBusy_thenThrowException() {
        when(orderRepository.existsByStatus(Status.IN_PROGRESS)).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.takeNextOrder();
        });
        assertThat(exception.getMessage()).isEqualTo("Pizza maker busy");
        verify(orderRepository, never()).findFirstByStatusOrderByCreatedAtAsc(any());
    }

    @Test
    @DisplayName("Complete order. There is one order in progress and no exception is thrown")
    void whenThereIsAnOrderInProgress_completeOrder(){
        Long id = 1L;
        PizzaOrder mockOrder = new PizzaOrder();
        mockOrder.setId(id);
        mockOrder.setStatus(Status.IN_PROGRESS);
        when(orderRepository.findById(id)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any(PizzaOrder.class))).thenAnswer(i -> i.getArguments()[0]);
        OrderResponseDTO order = orderService.completeOrder(id);
        assertThat(order.status()).isEqualTo(Status.COMPLETED);
        verify(orderRepository).save(mockOrder);
    }

    @Test
    @DisplayName("Should throw exception when order ID does not exist")
    void whenOrderIdDoesNotExist_thenThrowException() {
        Long wrongId = 999L;
        when(orderRepository.findById(wrongId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.completeOrder(wrongId);
        });
        assertThat(exception.getMessage()).isEqualTo("Wrong id!");
    }

    @Test
    @DisplayName("Should throw exception when order is not IN_PROGRESS")
    void whenOrderIsNotInProgress_completeOrder_thenThrowException() {
        Long id = 1L;
        PizzaOrder mockOrder = new PizzaOrder();
        mockOrder.setId(id);
        mockOrder.setStatus(Status.PLACED);
        when(orderRepository.findById(id)).thenReturn(Optional.of(mockOrder));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.completeOrder(id);
        });
        assertThat(exception.getMessage()).isEqualTo("Order is not in progress");
    }

    @Test
    @DisplayName("Should return the order when an order with the given code exists")
    void shouldReturnOrder_whenCodeExists(){
        String orderCode = "abc-123";
        PizzaOrder order = new PizzaOrder();
        order.setStatus(Status.PLACED);
        order.setOrderCode(orderCode);
        when(orderRepository.findByOrderCode(orderCode)).thenReturn(Optional.of(order));
        OrderResponseDTO result = orderService.getOrderByCode(orderCode);
        assertThat(result).isNotNull();
        assertThat(result.orderCode()).isEqualTo(orderCode);
        assertThat(result.status()).isEqualTo(Status.PLACED);
    }

    @Test
    @DisplayName("Should throw exception when the provided code does not exist")
    void shouldThrowException_whenCodeDoesNotExist() {
        String wrongCode = "NON-EXISTENT";
        when(orderRepository.findByOrderCode(wrongCode)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderByCode(wrongCode);
        });
        assertThat(exception.getMessage()).contains("Order not found with code: " + wrongCode);
    }

}
