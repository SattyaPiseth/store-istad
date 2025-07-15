package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Sattya
 * create at 2/7/2024 4:07 PM
 */
@Entity
@Table(name = "shop_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shopOrder")
    private List<OrderLineEntity> orderLines;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @OneToMany(mappedBy = "shopOrder")
    private List<UserPaymentMethodEntity> userPaymentMethods;

    @ManyToOne
    @JoinColumn(name = "shipping_method")
    private ShippingMethodEntity shippingMethod;

    @Column(name = "order_total",nullable = false,precision = 10,scale = 2)
    private BigDecimal orderTotal;

    @ManyToOne
    @JoinColumn(name = "order_status")
    private OrderStatusEntity orderStatus;

    @Column(name = "shipping_address",nullable = false)
    private String shippingAddress;
}
