package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Клас для виконання SQL-запитів
public class QueryExecutor {
    // Запит 1: Список безробітних на курсі
    public String executeQuery1(int courseId) throws SQLException {
        String sql = "SELECT u.full_name AS Безробітний, c.title AS Курс " +
                "FROM unemployed u " +
                "JOIN unemployed_course uc ON u.unemployed_id = uc.unemployed_id " +
                "JOIN course c ON uc.course_id = c.course_id " +
                "WHERE uc.course_id = ? " +
                "ORDER BY u.full_name";
        StringBuilder result = new StringBuilder("Список безробітних, що записані на певний курс:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.append(rs.getString("Безробітний")).append(" - ").append(rs.getString("Курс")).append("\n");
            }
        }
        return result.toString();
    }

    // Запит 2: Безробітні за першою літерою прізвища
    public String executeQuery2(String letter) throws SQLException {
        String sql = "SELECT * FROM unemployed WHERE full_name LIKE ?";
        StringBuilder result = new StringBuilder("Безробітні з прізвищем на '" + letter + "':\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, letter + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.append(rs.getString("full_name")).append("\n");
            }
        }
        return result.toString();
    }

    // Запит 3: Вакансії з зарплатою 15000-25000
    public String executeQuery3() throws SQLException {
        String sql = "SELECT * FROM vacancy WHERE salary BETWEEN 15000 AND 25000";
        StringBuilder result = new StringBuilder("Вакансії з зарплатою від 15000 до 25000:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.append(rs.getString("position_title")).append(" - ").append(rs.getDouble("salary")).append("\n");
            }
        }
        return result.toString();
    }

    // Запит 4: Кількість безробітних з досвідом > 3 років
    public String executeQuery4() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM unemployed WHERE experience > 3";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            return "Кількість безробітних з досвідом більше 3 років: " + rs.getInt("count") + "\n";
        }
    }

    // Запит 5: Кількість слухачів на кожному курсі
    public String executeQuery5() throws SQLException {
        String sql = "SELECT course_title AS \"Назва курсу\", COUNT(*) AS \"Кількість слухачів\" " +
                "FROM unemployed_course " +
                "GROUP BY course_title " +
                "ORDER BY COUNT(*) DESC";
        StringBuilder result = new StringBuilder("Кількість слухачів на кожному курсі:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.append(rs.getString("Назва курсу")).append(": ").append(rs.getInt("Кількість слухачів")).append("\n");
            }
        }
        return result.toString();
    }

    // Запит 6: Курс із найбільшою кількістю слухачів
    public String executeQuery6() throws SQLException {
        String sql = "SELECT course_title FROM unemployed_course " +
                "GROUP BY course_title " +
                "HAVING COUNT(*) >= ALL (SELECT COUNT(*) FROM unemployed_course GROUP BY course_title)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            return "Курс із найбільшою кількістю слухачів: " + rs.getString("course_title") + "\n";
        }
    }

    // Запит 7: Безробітні з найбільшим досвідом за спеціальністю
    public String executeQuery7() throws SQLException {
        String sql = "SELECT u.speciality, u.full_name, u.experience " +
                "FROM unemployed u " +
                "WHERE experience = (SELECT MAX(u2.experience) FROM unemployed u2 WHERE u2.speciality = u.speciality)";
        StringBuilder result = new StringBuilder("Безробітні з найбільшим досвідом за спеціальністю:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.append(rs.getString("speciality")).append(": ")
                        .append(rs.getString("full_name")).append(" (").append(rs.getInt("experience")).append(")\n");
            }
        }
        return result.toString();
    }

    // Запит 8: Безробітні, які не записані на курси
    public String executeQuery8() throws SQLException {
        String sql = "SELECT u.full_name FROM unemployed u " +
                "LEFT JOIN unemployed_course uc ON u.unemployed_id = uc.unemployed_id " +
                "WHERE uc.course_id IS NULL";
        StringBuilder result = new StringBuilder("Безробітні, які не записані на курси:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.append(rs.getString("full_name")).append("\n");
            }
        }
        return result.toString();
    }

    // Запит 9: Безробітні за рівнем досвіду
    public String executeQuery9() throws SQLException {
        String sql = "SELECT full_name, 'Без досвіду або мінімальний (0-2 роки)' AS коментар FROM unemployed WHERE experience BETWEEN 0 AND 2 " +
                "UNION " +
                "SELECT full_name, 'Середній досвід (3-5 роки)' AS коментар FROM unemployed WHERE experience BETWEEN 3 AND 5 " +
                "UNION " +
                "SELECT full_name, 'Великий досвід (понад 5 років)' AS коментар FROM unemployed WHERE experience > 5";
        StringBuilder result = new StringBuilder("Безробітні за рівнем досвіду:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.append(rs.getString("full_name")).append(": ").append(rs.getString("коментар")).append("\n");
            }
        }
        return result.toString();
    }
}