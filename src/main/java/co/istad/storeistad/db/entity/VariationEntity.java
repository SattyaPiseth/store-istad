package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Sattya
 * create at 2/7/2024 1:41 AM
 */
@Entity
@Table(name = "variations",indexes = {
        @Index(name = "idx_variation_name",columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private CategoryEntity category;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "variation")
    private List<VariationOptionEntity> options;

}
