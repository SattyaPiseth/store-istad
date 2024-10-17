package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sattya
 * create at 2/7/2024 12:13 PM
 */
@Entity
@Table(name = "shopping_cart_item",indexes = {
        @Index(name = "idx_shopping_cart_item_product_item_id",columnList = "product_item_id"),
        @Index(name = "idx_shopping_cart_item_cart_id",columnList = "cart_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "qty")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItemEntity productItem;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCartEntity shoppingCart;
}
