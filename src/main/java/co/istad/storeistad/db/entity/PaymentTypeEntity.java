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
 * create at 2/7/2024 9:29 PM
 */
@Entity
@Table(name = "payment_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value",nullable = false)
    private String value;

    @OneToMany(mappedBy = "paymentType")
    private List<UserPaymentMethodEntity> userPaymentMethods;
}
