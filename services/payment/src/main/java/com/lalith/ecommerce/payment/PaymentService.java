package com.lalith.ecommerce.payment;

import com.lalith.ecommerce.notification.NotificationProducer;
import com.lalith.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
        notificationProducer.sendPaymentNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.totalAmount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }

    public Payment getPaymentDetalis(Integer orderId) {
        return repository.findByOrderId(orderId);
    }
}
