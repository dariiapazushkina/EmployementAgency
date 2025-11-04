package org.example;

// Клас для перевірки спеціальності
public class SpecialityList {
    private static final String[] VALID_SPECIALITIES = {"Бухгалтер", "Електрик", "Менеджер з продажу", "Програміст", "Касир", "Продавець", "Консультант"};

    // Перевірка, чи є спеціальність в списку
    public boolean checkSpeciality(String speciality) {
        for (String valid : VALID_SPECIALITIES) {
            if (valid.equalsIgnoreCase(speciality)) {
                return true;
            }
        }
        return false;
    }
}
