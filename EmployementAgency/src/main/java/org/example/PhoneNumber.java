package org.example;

// Клас для перевірки номера телефону
public class PhoneNumber {
    private String number;

    public PhoneNumber(String number) {
        if (!isValid(number)) {
            throw new IllegalArgumentException("Невірний формат номера телефону");
        }
        this.number = number;
    }

    // Перевірка, чи номер складається з 10 цифр
    private boolean isValid(String number) {
        return number != null && number.matches("\\d{10}");
    }

    public String getNumber() {
        return number;
    }
}