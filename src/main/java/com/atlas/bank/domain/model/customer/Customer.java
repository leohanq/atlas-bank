package com.atlas.bank.domain.model.customer;


import com.atlas.bank.domain.model.shared.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Customer {


    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private Email email;
    private CustomerStatus status;
    private LocalDateTime createdAt;

    public void initialDefaults() {
        if (this.status == null) this.status = CustomerStatus.ACTIVE;
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.status == CustomerStatus.ACTIVE;
    }
}
