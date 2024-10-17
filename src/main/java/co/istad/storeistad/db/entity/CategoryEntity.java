package co.istad.storeistad.db.entity;


import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @author Sattya
 * create at 1/27/2024 1:38 PM
 */
@Entity
@Table(name = "categories",indexes = {
        @Index(name = "idx_category_name",columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true,nullable = false)
    private String uuid;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String images;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "category")
    private List<VariationEntity> variations;

    @ManyToMany(mappedBy = "categories")
    private Set<PromotionEntity> promotions;

}
