package com.kredilendirme.loan.customer.repository;

import com.kredilendirme.loan.config.JpaAuditingConfig;
import com.kredilendirme.loan.customer.entity.Customer;
import com.kredilendirme.loan.customer.entity.CustomerStatus;
import com.kredilendirme.loan.customer.entity.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Import(JpaAuditingConfig.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.save(new Customer("10001", CustomerType.SME,
                "ABC Teknoloji A.S.", "1234567890"));
        customerRepository.save(new Customer("10002", CustomerType.CORPORATE,
                "Deniz Insaat A.S.", "9876543210"));
    }

    @Test
    @DisplayName("findByCustomerNumber returns the matching customer")
    void shouldFindByCustomerNumber() {
        Optional<Customer> found = customerRepository.findByCustomerNumber("10001");

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("ABC Teknoloji A.S.");
    }

    @Test
    @DisplayName("findByCustomerNumber returns empty for an unknown number")
    void shouldReturnEmptyForUnknownCustomerNumber() {
        assertThat(customerRepository.findByCustomerNumber("99999")).isEmpty();
    }

    @Test
    @DisplayName("existsByIdentifier detects duplicates")
    void shouldDetectExistingIdentifier() {
        assertThat(customerRepository.existsByIdentifier("1234567890")).isTrue();
        assertThat(customerRepository.existsByIdentifier("0000000000")).isFalse();
    }

    @Test
    @DisplayName("title search is case insensitive and partial")
    void shouldSearchByTitleIgnoringCase() {
        Page<Customer> result = customerRepository
                .findByTitleContainingIgnoreCase("teknoloji", PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getCustomerNumber()).isEqualTo("10001");
    }

    @Test
    @DisplayName("auditing fills created and updated timestamps on save")
    void shouldFillAuditFields() {
        Customer saved = customerRepository.findByCustomerNumber("10001").get();

        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();
    }

    @Test
    @DisplayName("type and status filter matches only active customers of that type")
    void shouldFilterByTypeAndStatus() {
        Page<Customer> smes = customerRepository.findByCustomerTypeAndStatus(
                CustomerType.SME, CustomerStatus.ACTIVE, PageRequest.of(0, 10));

        assertThat(smes.getTotalElements()).isEqualTo(1);
    }
}
