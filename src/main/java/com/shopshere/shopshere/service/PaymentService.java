package com.shopshere.shopshere.service;

import com.shopshere.shopshere.entity.Payment;
import com.shopshere.shopshere.entity.enums.PaymentStatus;

public interface PaymentService {
    Payment createPayment(Long orderId);
    Payment updateStatus(Long paymentId, PaymentStatus status);
}