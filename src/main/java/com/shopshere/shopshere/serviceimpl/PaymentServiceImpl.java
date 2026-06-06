package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.entity.Order;
import com.shopshere.shopshere.entity.OrderItem;
import com.shopshere.shopshere.entity.Payment;
import com.shopshere.shopshere.entity.Product;
import com.shopshere.shopshere.entity.enums.OrderStatus;
import com.shopshere.shopshere.entity.enums.PaymentStatus;
import com.shopshere.shopshere.repository.OrderRepository;
import com.shopshere.shopshere.repository.PaymentRepository;
import com.shopshere.shopshere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public Payment createPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updateStatus(Long paymentId, PaymentStatus status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(status);

        Order order = payment.getOrder();

        // 🔥 CORE LOGIC
        if (status == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.CONFIRMED);
            order.setPaymentStatus(PaymentStatus.SUCCESS);
        } else if (status == PaymentStatus.FAILED) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setPaymentStatus(PaymentStatus.FAILED);

            // 🔥 RESTORE STOCK (VERY IMPORTANT)
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
            }
        }

        return paymentRepository.save(payment);
    }
}