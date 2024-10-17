package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import co.istad.storeistad.db.status.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sattya
 * create at 2/7/2024 9:26 PM
 */
@Entity
@Table(name = "user_payment_method")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentMethodEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentTypeEntity paymentType;

    @Column(name = "provider",nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "account_number",nullable = false)
    private String accountNumber;

    @Column(name = "expiry_date",nullable = false)
    private String expiryDate;

    @Column(name = "is_default",nullable = false)
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private ShopOrderEntity shopOrder;
}
