package com.kredilendirme.loan.customer.service;

import com.kredilendirme.loan.common.exception.BusinessException;
import com.kredilendirme.loan.common.exception.ResourceNotFoundException;
import com.kredilendirme.loan.customer.dto.CreateCustomerRequest;
import com.kredilendirme.loan.customer.dto.CustomerResponse;
import com.kredilendirme.loan.customer.entity.Address;
import com.kredilendirme.loan.customer.entity.Customer;
import com.kredilendirme.loan.customer.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TaxNumberValidator taxNumberValidator;

    public CustomerService(CustomerRepository customerRepository,
                           TaxNumberValidator taxNumberValidator) {
        this.customerRepository = customerRepository;
        this.taxNumberValidator = taxNumberValidator;
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        if (!taxNumberValidator.isValidFor(request.getCustomerType(), request.getIdentifier())) {
            throw new BusinessException("Kimlik / vergi numarası geçersiz");
        }
        if (customerRepository.existsByIdentifier(request.getIdentifier())) {
            throw new BusinessException("Bu kimlik / vergi numarasıyla kayıtlı müşteri zaten var");
        }

        String customerNumber = String.valueOf(customerRepository.nextCustomerNumber());

        Customer customer = new Customer(customerNumber, request.getCustomerType(),
                request.getTitle(), request.getIdentifier());
        customer.setTaxOffice(request.getTaxOffice());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setExporter(request.isExporter());
        customer.setAddress(new Address(request.getAddressLine(), request.getDistrict(),
                request.getCity(), request.getPostalCode()));

        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse get(Long id) {
        return CustomerResponse.from(findCustomer(id));
    }

    public CustomerResponse getByCustomerNumber(String customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber)
                .map(CustomerResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Müşteri bulunamadı: " + customerNumber));
    }

    public Page<CustomerResponse> search(String title, Pageable pageable) {
        if (title == null || title.trim().isEmpty()) {
            return customerRepository.findAll(pageable).map(CustomerResponse::from);
        }
        return customerRepository.findByTitleContainingIgnoreCase(title.trim(), pageable)
                .map(CustomerResponse::from);
    }

    @Transactional
    public CustomerResponse update(Long id, CreateCustomerRequest request) {
        Customer customer = findCustomer(id);
        customer.setTitle(request.getTitle());
        customer.setTaxOffice(request.getTaxOffice());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setExporter(request.isExporter());
        customer.setAddress(new Address(request.getAddressLine(), request.getDistrict(),
                request.getCity(), request.getPostalCode()));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void deactivate(Long id) {
        Customer customer = findCustomer(id);
        if (!customer.isActive()) {
            throw new BusinessException("Müşteri zaten pasif durumda");
        }
        customer.deactivate();
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı: " + id));
    }
}
