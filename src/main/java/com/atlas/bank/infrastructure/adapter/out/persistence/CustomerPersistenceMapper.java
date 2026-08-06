package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.domain.model.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public CustomerJpaEntity toJpaEntity(Customer customer) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setStatus(customer.getStatus());
        entity.setCreatedAt(customer.getCreatedAt());
        return entity;
    }
}
