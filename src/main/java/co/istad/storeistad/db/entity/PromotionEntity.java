package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

/**
 * @author Sattya
 * create at 2/7/2024 2:22 AM
 */
@Entity
@Table(name = "promotions", indexes = {
        @Index(name = "idx_promotion_uuid", columnList = "uuid", unique = true),
        @Index(name = "idx_promotion_name", columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "discount_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal discountRate;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "promotion_category", joinColumns = @JoinColumn(name = "promotion_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<CategoryEntity> categories;

}
