package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author Sattya
 * create at 2/7/2024 1:45 AM
 */
@Entity
@Table(name = "variation_options",indexes = {
        @Index(name = "idx_variation_option_value",columnList = "value")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariationOptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String value;

    @ManyToOne
    private VariationEntity variation;

    @ManyToMany(mappedBy = "variationOptions")
    private Set<ProductItemEntity> productItems;
}
