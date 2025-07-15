package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Sattya
 * create at 2/7/2024 12:17 PM
 */
@Entity
@Table(name = "shopping_cart",indexes = {
        @Index(name = "idx_shopping_cart_user_id",columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartItemEntity> shoppingCartItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
