package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// Клас для збереження даних безробітного в базу
public class RegistrationRecord {
    // Зберігає дані в таблиці unemployed
    public void saveUnemployed(String fullName, LocalDate birthDate, String address, String phone, String speciality, int experience) throws SQLException {
        String sql = "INSERT INTO unemployed (full_name, birth_date, address, phone, speciality, experience) " + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setDate(2, java.sql.Date.valueOf(birthDate));
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setString(5, speciality);
            stmt.setInt(6, experience);
            stmt.executeUpdate();
        }
    }
}