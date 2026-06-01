package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.Dto.OrderPaymentResponseDTO;
import com.firstContact.projetoUm.entity.Order;
import com.firstContact.projetoUm.entity.OrderItem;
import com.firstContact.projetoUm.entity.Payment;
import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.entity.enums.OrderStatus;
import com.firstContact.projetoUm.repositories.OrderItemRepository;
import com.firstContact.projetoUm.repositories.OrderRepository;
import com.firstContact.projetoUm.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.time.Instant.now;

@Service
public class OrderService {

    @Autowired 
    private OrderRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private StripeService stripeService;

    @Transactional
    public OrderPaymentResponseDTO insert(Order obj){
        obj.setMoment(now());
        obj.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        obj = repository.save(obj);

        for(OrderItem item : obj.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            item.setOrder(obj);
            item.setProduct(product);
            item.setPrice(product.getPrice());
        }

        orderItemRepository.saveAll(obj.getItems());
        Order savedOrder = repository.save(obj);

        try{
            String checkoutURl = stripeService.createCheckoutSession(savedOrder);

            return new OrderPaymentResponseDTO(savedOrder, checkoutURl);
        } catch (Exception e){
            throw new RuntimeException("Erro ao gerar sessão de pagamento stripe " + e.getMessage() );
        }
    }

    @Transactional
    public void updateStatusToPaid(Long orderId) {
        // 1. Busca o pedido no banco pelo ID enviado pelo Webhook da Stripe
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order id " + orderId + " not found"));

        // 2. Altera o status usando o seu Enum (2 - PAID)
        order.setOrderStatus(OrderStatus.PAID);

        // 3. Cria e associa o pagamento ao pedido (salva no banco em cascata)
        Payment payment = new Payment(null, java.time.Instant.now(), order);
        order.setPayment(payment);

        // 4. Salva o pedido atualizado de volta no banco
        repository.save(order);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = repository.findById(id);
        return obj.get();
    }

}
