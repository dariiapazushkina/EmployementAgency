package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Клас для графічного інтерфейсу програми
public class UnemployedRegistrationGUI extends JFrame {
    private JTextField fullNameField, birthDateField, addressField, phoneField, specialityField, experienceField;
    private JTextField courseIdField, letterField;
    private RegistrationForm registrationForm;
    private QueryExecutor queryExecutor;

    public UnemployedRegistrationGUI() {
        registrationForm = new RegistrationForm();
        queryExecutor = new QueryExecutor();
        setTitle("Служба зайнятості");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Налаштування вкладок
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));

        // Вкладка 1: Реєстрація
        JPanel registrationPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        registrationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        registrationPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Реєстрація безробітного", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        registrationPanel.add(titleLabel);
        registrationPanel.add(new JLabel(""));

        registrationPanel.add(createLabel("ПІБ*:"));
        fullNameField = createTextField("Введіть прізвище, ім'я, по батькові");
        registrationPanel.add(fullNameField);

        registrationPanel.add(createLabel("Дата народження (YYYY-MM-DD)*:"));
        birthDateField = createTextField("Формат: 1995-04-23");
        registrationPanel.add(birthDateField);

        registrationPanel.add(createLabel("Адреса*:"));
        addressField = createTextField("Введіть адресу (наприклад, вул. Незалежності, 15)");
        registrationPanel.add(addressField);

        registrationPanel.add(createLabel("Телефон*:"));
        phoneField = createTextField("10 цифр, наприклад, 0501234567");
        registrationPanel.add(phoneField);

        registrationPanel.add(createLabel("Спеціальність*:"));
        specialityField = createTextField("Наприклад, Бухгалтер, Програміст");
        registrationPanel.add(specialityField);

        registrationPanel.add(createLabel("Стаж (роки)*:"));
        experienceField = createTextField("Введіть кількість років досвіду");
        registrationPanel.add(experienceField);

        JButton registerButton = new JButton("Зареєструвати");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
        registerButton.setToolTipText("Зареєструвати безробітного в системі");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String fullName = fullNameField.getText().trim();
                    String birthDate = birthDateField.getText().trim();
                    String address = addressField.getText().trim();
                    String phone = phoneField.getText().trim();
                    String speciality = specialityField.getText().trim();
                    String experience = experienceField.getText().trim();

                    // Перевірка заповненості всіх полів
                    if (fullName.isEmpty() || birthDate.isEmpty() || address.isEmpty() ||
                            phone.isEmpty() || speciality.isEmpty() || experience.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Перевірте, чи заповнені поля", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int exp = Integer.parseInt(experience);
                    registrationForm.registerUnemployed(fullName, birthDate, address, phone, speciality, exp);
                    JOptionPane.showMessageDialog(null, "Реєстрація успішна!", "Успіх", JOptionPane.INFORMATION_MESSAGE);
                    clearRegistrationFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Стаж має бути числом", "Помилка", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Помилка: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        registrationPanel.add(registerButton);

        tabbedPane.addTab("Реєстрація", registrationPanel);

        // Вкладка 2: Запити
        JPanel queryPanel = new JPanel(new BorderLayout());
        queryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        queryPanel.setBackground(new Color(245, 245, 245));

        // Панель для введення параметрів
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(new Color(245, 245, 245));

        inputPanel.add(createLabel("ID курсу:"));
        courseIdField = createTextField("Введіть ID курсу для запиту 'Список безробітних, що записані на певний курс'");
        inputPanel.add(courseIdField);

        inputPanel.add(createLabel("Літера:"));
        letterField = createTextField("Введіть першу літеру прізвища для запиту 'Безробітні з прізвищем на задану літеру'");
        inputPanel.add(letterField);

        // Панель для кнопок запитів
        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 5, 5));
        buttonPanel.setBackground(new Color(245, 245, 245));

        String[] queryNames = {
                "Список безробітних, що записані на певний курс",
                "Безробітні з прізвищем на задану літеру",
                "Вакансії з зарплатою від 15000 до 25000",
                "Кількість безробітних з досвідом більше 3 років",
                "Кількість слухачів на кожному курсі",
                "Курс із найбільшою кількістю слухачів",
                "Безробітні з найбільшим досвідом за спеціальністю",
                "Безробітні, які не записані на курси",
                "Безробітні за рівнем досвіду"
        };

        String[] queryToolTips = {
                "Показує безробітних, записаних на вказаний курс",
                "Показує безробітних, чиї прізвища починаються на задану літеру",
                "Показує вакансії із зарплатою в межах 15000–25000 грн",
                "Підраховує кількість безробітних із досвідом більше 3 років",
                "Показує кількість слухачів для кожного курсу",
                "Визначає курс із найбільшою кількістю слухачів",
                "Показує безробітних із максимальним досвідом для кожної спеціальності",
                "Показує безробітних, які не записані на жоден курс",
                "Класифікує безробітних за рівнем досвіду (0-2, 3-5, >5 років)"
        };

        JButton[] queryButtons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int queryNumber = i + 1;
            queryButtons[i] = new JButton(queryNames[i]);
            queryButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            queryButtons[i].setPreferredSize(new Dimension(350, 30));
            queryButtons[i].setToolTipText(queryToolTips[i]);
            queryButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String result = "";
                        switch (queryNumber) {
                            case 1:
                                String courseId = courseIdField.getText().trim();
                                if (courseId.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Перевірте, чи заповнені поля", "Помилка", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                result = queryExecutor.executeQuery1(Integer.parseInt(courseId));
                                break;
                            case 2:
                                String letter = letterField.getText().trim();
                                if (letter.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Перевірте, чи заповнені поля", "Помилка", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                result = queryExecutor.executeQuery2(letter.toUpperCase());
                                break;
                            case 3:
                                result = queryExecutor.executeQuery3();
                                break;
                            case 4:
                                result = queryExecutor.executeQuery4();
                                break;
                            case 5:
                                result = queryExecutor.executeQuery5();
                                break;
                            case 6:
                                result = queryExecutor.executeQuery6();
                                break;
                            case 7:
                                result = queryExecutor.executeQuery7();
                                break;
                            case 8:
                                result = queryExecutor.executeQuery8();
                                break;
                            case 9:
                                result = queryExecutor.executeQuery9();
                                break;
                        }
                        showResultDialog(result);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID курсу має бути числом", "Помилка", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Помилка: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            buttonPanel.add(queryButtons[i]);
        }

        // Додаємо прокрутку до панелі кнопок
        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
        buttonScrollPane.setBorder(BorderFactory.createEmptyBorder());

        queryPanel.add(inputPanel, BorderLayout.NORTH);
        queryPanel.add(buttonScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Запити", queryPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    // Створює мітку з текстом
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    // Створює текстове поле з підказкою
    private JTextField createTextField(String toolTip) {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setToolTipText(toolTip);
        return field;
    }

    // Очищає поля реєстрації
    private void clearRegistrationFields() {
        fullNameField.setText("");
        birthDateField.setText("");
        addressField.setText("");
        phoneField.setText("");
        specialityField.setText("");
        experienceField.setText("");
    }

    // Відображає результати запиту в діалоговому вікні
    private void showResultDialog(String result) {
        JTextArea resultArea = new JTextArea(result);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Результати запиту", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UnemployedRegistrationGUI());
    }
}