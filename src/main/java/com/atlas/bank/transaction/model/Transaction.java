package com.atlas.bank.transaction.model;

import com.atlas.bank.transaction.model.state.ExecutedState;
import com.atlas.bank.transaction.model.state.PendingState;
import com.atlas.bank.transaction.model.state.RejectedState;
import com.atlas.bank.transaction.model.state.ReversedState;
import com.atlas.bank.transaction.model.state.TransactionState;
import com.atlas.bank.transaction.model.state.ValidatedState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionType type; // DEPOSIT, WITHDRAWAL, TRANSFER
    @Column(name = "source_account_id", nullable = false)
    private Long sourceAccountId;

    @Column(name = "target_account_id", nullable = false)
    private Long targetAccountId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private BigDecimal fee;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING, EXECUTED, REJECTED
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private TransactionState state;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = TransactionStatus.EXECUTED;
    }

    public TransactionState getState() {
        if (state == null) {
            state= switch (this.status) {
                case PENDING -> new PendingState();
                case VALIDATED -> new ValidatedState();
                case EXECUTED -> new ExecutedState();
                case REJECTED -> new RejectedState();
                case REVERSED -> new ReversedState();
            };
        }

        return state;
    }

    public void advanceToNextState(TransactionState newState) {
        this.state = newState;
        this.status = newState.status();
    }
}