package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sattya
 * create at 2/7/2024 3:55 PM
 */
@Entity
@Table(name = "order_line")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "orderLine")
    private List<UserReviewEntity> userReviews;

    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItemEntity productItem;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShopOrderEntity shopOrder;

    @Column(name = "qty")
    private Integer quantity;

    @Column(name = "price",nullable = false,precision = 10,scale = 2)
    private BigDecimal price;
}
