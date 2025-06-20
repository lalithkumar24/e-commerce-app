package com.lalith.ecommerce.kafka;

import com.lalith.ecommerce.email.EmailService;
import com.lalith.ecommerce.kafka.order.OrderConformation;
import com.lalith.ecommerce.kafka.payment.PaymentConformation;
import com.lalith.ecommerce.kafka.payment.PaymentMethod;
import com.lalith.ecommerce.notification.Notification;
import com.lalith.ecommerce.notification.NotificationRepository;
import com.lalith.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.lalith.ecommerce.notification.NotificationType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConformation paymentConformation) throws MessagingException {
        log.info("Received Payment Success Notification: {}", paymentConformation);
        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFORMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConformation(paymentConformation)
                        .build()
        );
        var customerName = paymentConformation.customerFirstName() + " " + paymentConformation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentConformation.customerEmail(),
                customerName,
                paymentConformation.TotalAmount(),
                paymentConformation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConformationNotification(OrderConformation orderConformation) throws MessagingException {
        log.info("Received Payment Success Notification: {}", orderConformation);
        repository.save(
                Notification.builder()
                        .type(ORDER_CONFORMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConformation(orderConformation)
                        .build()
        );
        var customerName = orderConformation.customer().firstName() + " " + orderConformation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConformation.customer().email(),
                customerName,
                orderConformation.totalAmount(),
                orderConformation.orderReference(),
                orderConformation.products()
        );
    }
}
