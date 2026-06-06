package com.shopshere.shopshere.controller;

import com.shopshere.shopshere.entity.Payment;
import com.shopshere.shopshere.entity.enums.PaymentStatus;
import com.shopshere.shopshere.service.PaymentService;
import com.shopshere.shopshere.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    // 💳 CREATE PAYMENT (after order placed)
    @PostMapping("/create/{orderId}")
    public Payment createPayment(@PathVariable Long orderId) {
        return paymentService.createPayment(orderId);
    }

    // 🔄 UPDATE PAYMENT STATUS (simulate success/failure)
    @PutMapping("/{paymentId}/status")
    public Payment updateStatus(
            @PathVariable Long paymentId,
            @RequestParam PaymentStatus status
    ) {
        return paymentService.updateStatus(paymentId, status);
    }

    // 🔍 GET PAYMENT BY ORDER
    @GetMapping("/order/{orderId}")
    public Payment getByOrder(@PathVariable Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}