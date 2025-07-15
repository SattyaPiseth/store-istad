package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author Sattya
 * create at 2/7/2024 1:22 AM
 */
@Entity
@Table(name = "product_items",indexes = {
        @Index(name = "idx_product_item_sku",columnList = "SKU",unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SKU",nullable = false,unique = true)
    private String code;

    @Column(name = "qty_in_stock",nullable = false)
    private Integer quantity;

    @Column(name = "product_image")
    private String image;

    @Column(name = "price",nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToMany(mappedBy = "productItem")
    private List<ShoppingCartItemEntity> shoppingCartItems;

    @OneToMany(mappedBy = "productItem")
    private List<OrderLineEntity> orderLines;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_configuration",
            joinColumns = @JoinColumn(name = "product_item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "variation_option_id", referencedColumnName = "id"))
    private Set<VariationOptionEntity> variationOptions;

}
