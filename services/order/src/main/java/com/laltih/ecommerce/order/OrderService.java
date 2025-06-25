package com.laltih.ecommerce.order;

import com.laltih.ecommerce.customer.CustomerClient;
import com.laltih.ecommerce.exception.BusinessException;
import com.laltih.ecommerce.kafka.OrderConfirmation;
import com.laltih.ecommerce.kafka.OrderProducer;
import com.laltih.ecommerce.orderline.OrderLineRequest;
import com.laltih.ecommerce.orderline.OrderLineService;
import com.laltih.ecommerce.payment.PaymentClient;
import com.laltih.ecommerce.payment.PaymentRequest;
import com.laltih.ecommerce.product.ProductClient;
import com.laltih.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private  final ProductClient productClient;
    private  final OrderMapper mapper;
    private  final OrderLineService orderLineService;
    private  final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public Integer createdOrder(@Valid OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can't create order with id " + request.customerId() + " because customer not found"));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()){
            System.out.println("Product ID: " + purchaseRequest.productId());

            if (purchaseRequest.productId() == null) {
                throw new IllegalArgumentException("Product ID in request is null");
            }

            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository
                .findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new BusinessException("Can't find order with id " + orderId));
    }

}
