package com.laltih.ecommerce.orderline;


import com.laltih.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLine {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    private Integer productId;
    // we can also add attrubite like sale price @ that point in time for annaylis
    private Double quantity;


}
