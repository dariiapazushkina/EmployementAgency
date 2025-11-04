package org.example;

// Клас для роботи зі стажем
public class WorkBook {
    private int experience;

    public WorkBook(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Стаж не може бути від'ємним");
        }
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }
}
