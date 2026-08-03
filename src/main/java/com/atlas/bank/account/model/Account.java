package com.atlas.bank.account.model;

import com.atlas.bank.account.exception.InsufficientFundsException;
import com.atlas.bank.customer.model.Email;
import com.atlas.bank.shared.model.Currency;
import com.atlas.bank.shared.model.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;
    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false))
    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type; // SAVING, CHECKING

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false, length = 3))
    })
    @Embedded
    private Money balance;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus status; // ACTIVE, FROZEN, CLOSED
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "customer_id")
    private Long customerId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.COP);
    }


    public void deposit(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount.isGreaterOrEqual(this.balance)) {
            throw new InsufficientFundsException(id, balance.getAmount(), amount.getAmount());
        }
        this.balance = this.balance.subtract(amount);
    }
}
