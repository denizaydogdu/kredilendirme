package com.kredilendirme.loan.customer.repository;

import com.kredilendirme.loan.customer.entity.Customer;
import com.kredilendirme.loan.customer.entity.CustomerStatus;
import com.kredilendirme.loan.customer.entity.CustomerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerNumber(String customerNumber);

    Optional<Customer> findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    Page<Customer> findByCustomerTypeAndStatus(CustomerType customerType,
                                               CustomerStatus status,
                                               Pageable pageable);

    Page<Customer> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select c from Customer c join fetch c.financialInfo where c.id = :id")
    Optional<Customer> findByIdWithFinancialInfo(@Param("id") Long id);

    @Query(value = "select nextval('customer_number_seq')", nativeQuery = true)
    long nextCustomerNumber();
}
