package com.shopshere.shopshere.repository;

import com.shopshere.shopshere.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 🔍 Get payment by order ID (very useful)
    Optional<Payment> findByOrderId(Long orderId);
}