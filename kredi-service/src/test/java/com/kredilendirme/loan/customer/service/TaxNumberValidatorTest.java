package com.kredilendirme.loan.customer.service;

import com.kredilendirme.loan.customer.entity.CustomerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TaxNumberValidatorTest {

    private final TaxNumberValidator validator = new TaxNumberValidator();

    @Test
    @DisplayName("valid TCKN passes checksum validation")
    void shouldAcceptValidTckn() {
        assertThat(validator.isValidTckn("12345678950")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "12345678951",  // wrong check digits
            "11111111111",  // fails second checksum
            "02345678950",  // cannot start with zero
            "1234567895",   // too short
            "123456789501", // too long
            "1234567895a"   // non-numeric
    })
    @DisplayName("invalid TCKN values are rejected")
    void shouldRejectInvalidTckn(String value) {
        assertThat(validator.isValidTckn(value)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectNullAndEmptyTckn(String value) {
        assertThat(validator.isValidTckn(value)).isFalse();
    }

    @Test
    @DisplayName("valid VKN passes checksum validation")
    void shouldAcceptValidVkn() {
        assertThat(validator.isValidVkn("1234567890")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567891", "123456789", "12345678901", "abcdefghij"})
    @DisplayName("invalid VKN values are rejected")
    void shouldRejectInvalidVkn(String value) {
        assertThat(validator.isValidVkn(value)).isFalse();
    }

    @Test
    @DisplayName("individual customers are validated against TCKN, legal entities against VKN")
    void shouldPickAlgorithmByCustomerType() {
        assertThat(validator.isValidFor(CustomerType.INDIVIDUAL, "12345678950")).isTrue();
        assertThat(validator.isValidFor(CustomerType.INDIVIDUAL, "1234567890")).isFalse();
        assertThat(validator.isValidFor(CustomerType.SME, "1234567890")).isTrue();
        assertThat(validator.isValidFor(CustomerType.CORPORATE, "1234567890")).isTrue();
        assertThat(validator.isValidFor(null, "1234567890")).isFalse();
    }
}
