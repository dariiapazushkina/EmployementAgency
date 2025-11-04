package org.example;

// Клас для перевірки адреси
public class Map {
    private static final String[] SERVICED_STREETS = {"вул. Незалежності", "пр. Перемоги", "вул. Миру", "пл. Центральна"};

    // Перевірка, чи належить адреса  до району обслуговування
    public boolean checkAddress(String address) {
        for (String street : SERVICED_STREETS) {
            if (address.contains(street)) {
                return true;
            }
        }
        return false;
    }
}
