package com.kredilendirme.loan.customer.service;

import com.kredilendirme.loan.common.exception.BusinessException;
import com.kredilendirme.loan.common.exception.ResourceNotFoundException;
import com.kredilendirme.loan.customer.dto.CreateCustomerRequest;
import com.kredilendirme.loan.customer.dto.CustomerResponse;
import com.kredilendirme.loan.customer.entity.Customer;
import com.kredilendirme.loan.customer.entity.CustomerStatus;
import com.kredilendirme.loan.customer.entity.CustomerType;
import com.kredilendirme.loan.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TaxNumberValidator taxNumberValidator;

    @InjectMocks
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    private CreateCustomerRequest request;

    @BeforeEach
    void setUp() {
        request = new CreateCustomerRequest();
        request.setCustomerType(CustomerType.SME);
        request.setTitle("ABC Teknoloji A.S.");
        request.setIdentifier("1234567890");
        request.setExporter(true);
    }

    @Test
    @DisplayName("create persists customer with a sequence-based customer number")
    void shouldCreateCustomer() {
        when(taxNumberValidator.isValidFor(CustomerType.SME, "1234567890")).thenReturn(true);
        when(customerRepository.existsByIdentifier("1234567890")).thenReturn(false);
        when(customerRepository.nextCustomerNumber()).thenReturn(10001L);
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CustomerResponse response = customerService.create(request);

        verify(customerRepository).save(customerCaptor.capture());
        Customer saved = customerCaptor.getValue();
        assertThat(saved.getCustomerNumber()).isEqualTo("10001");
        assertThat(saved.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
        assertThat(saved.isExporter()).isTrue();
        assertThat(response.getTitle()).isEqualTo("ABC Teknoloji A.S.");
    }

    @Test
    @DisplayName("create rejects an identifier that fails checksum validation")
    void shouldRejectInvalidIdentifier() {
        when(taxNumberValidator.isValidFor(CustomerType.SME, "1234567890")).thenReturn(false);

        assertThatThrownBy(() -> customerService.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("geçersiz");

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("create rejects a duplicate identifier")
    void shouldRejectDuplicateIdentifier() {
        when(taxNumberValidator.isValidFor(CustomerType.SME, "1234567890")).thenReturn(true);
        when(customerRepository.existsByIdentifier("1234567890")).thenReturn(true);

        assertThatThrownBy(() -> customerService.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("zaten var");

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("get throws not-found for an unknown id")
    void shouldThrowNotFoundForUnknownId() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.get(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    @DisplayName("deactivate rejects a customer that is already passive")
    void shouldRejectDeactivatingPassiveCustomer() {
        Customer customer = new Customer("10001", CustomerType.SME,
                "ABC Teknoloji A.S.", "1234567890");
        customer.deactivate();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> customerService.deactivate(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("zaten pasif");
    }
}
