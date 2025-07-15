package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * @author Sattya
 * create at 2/7/2024 3:53 PM
 */
@Entity
@Table(name = "user_review",indexes = {
        @Index(name = "idx_user_review_user_id", columnList = "user_id"),
        @Index(name = "idx_user_review_ordered_product_id", columnList = "ordered_product_id"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "ordered_product_id")
    private OrderLineEntity orderLine;

    @Column(name = "rating_value")
//    @Size(max = 5)
    @Range(max = 5)
    @Positive
    private Integer ratingValue;

    @Size(max = 200)
    @Column(name = "comment")
    private String comment;
}
