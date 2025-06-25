package com.laltih.ecommerce.orderline;

import com.laltih.ecommerce.order.Order;
import com.laltih.ecommerce.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderRepository orderRepository;
    private final OrderLineMapper mapper;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        Order managedOrder = orderRepository.getReferenceById(orderLineRequest.Orderid());
        OrderLine line = mapper.toOrderLine(orderLineRequest);
        line.setOrder(managedOrder);
        return repository.save(line).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId).stream().map(mapper::toOrderLineResponse).collect(Collectors.toList());
    }
}
