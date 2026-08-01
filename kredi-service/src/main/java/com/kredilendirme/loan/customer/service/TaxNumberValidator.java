package com.kredilendirme.loan.customer.service;

import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class TaxNumberValidator {

    public boolean isValidTckn(String value) {
        if (value == null || !value.matches("[1-9]\\d{10}")) {
            return false;
        }
        int[] digits = value.chars().map(Character::getNumericValue).toArray();

        int oddSum = IntStream.of(0, 2, 4, 6, 8).map(i -> digits[i]).sum();
        int evenSum = IntStream.of(1, 3, 5, 7).map(i -> digits[i]).sum();
        int d10 = ((oddSum * 7) - evenSum) % 10;
        int d11 = IntStream.range(0, 10).map(i -> digits[i]).sum() % 10;

        return d10 >= 0 && digits[9] == d10 && digits[10] == d11;
    }

    public boolean isValidVkn(String value) {
        if (value == null || !value.matches("\\d{10}")) {
            return false;
        }
        int[] digits = value.chars().map(Character::getNumericValue).toArray();

        int sum = IntStream.range(0, 9).map(i -> {
            int tmp = (digits[i] + 10 - (i + 1)) % 10;
            return tmp == 9 ? 9 : (tmp * (1 << (9 - i))) % 9;
        }).sum();

        int checkDigit = (10 - (sum % 10)) % 10;
        return digits[9] == checkDigit;
    }

    public boolean isValidFor(com.kredilendirme.loan.customer.entity.CustomerType type,
                              String identifier) {
        if (type == null) {
            return false;
        }
        switch (type) {
            case INDIVIDUAL:
                return isValidTckn(identifier);
            case SME:
            case CORPORATE:
                return isValidVkn(identifier);
            default:
                return false;
        }
    }
}
