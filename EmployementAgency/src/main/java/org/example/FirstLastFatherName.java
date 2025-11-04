package org.example;

// Клас для перевірки формату ПІБ
public class FirstLastFatherName {
    private String fullName;

    public FirstLastFatherName(String fullName) {
        if (fullName == null || !fullName.matches("[А-ЯҐЄІЇа-яґєії]+\\s[А-ЯҐЄІЇа-яґєії]+\\s[А-ЯҐЄІЇа-яґєії]+")) {
            throw new IllegalArgumentException("Невірний формат ПІБ");
        }
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
