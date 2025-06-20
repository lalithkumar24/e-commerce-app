package com.lalith.ecommerce.notification;

import com.lalith.ecommerce.kafka.payment.PaymentConformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
}
