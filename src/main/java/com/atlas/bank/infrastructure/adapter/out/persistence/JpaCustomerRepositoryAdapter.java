package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.application.port.out.CustomerRepositoryPort;
import com.atlas.bank.domain.model.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository springDataCustomerRepository;
    private final CustomerPersistenceMapper customerEntityMapper;

    @Override
    public Optional<Customer> findById(Long id) {
        return springDataCustomerRepository.findById(id)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        customer.initialDefaults();
        var customerEntity = customerEntityMapper.toJpaEntity(customer);
        var savedEntity = springDataCustomerRepository.save(customerEntity);
        return customerEntityMapper.toDomain(savedEntity);
    }
}
