package org.example;
import java.time.LocalDate;

// Клас для процесу реєстрації
public class RegistrationForm {
    private PhoneNumber phoneNumber;
    private Map map;
    private FirstLastFatherName fullName;
    private SpecialityList specialityList;
    private WorkBook workBook;
    private RegistrationRecord registrationRecord;

    public RegistrationForm() {
        this.map = new Map();
        this.specialityList = new SpecialityList();
        this.registrationRecord = new RegistrationRecord();
    }

    // Реєструє безробітного з перевірками
    public void registerUnemployed(String fullNameStr, String birthDateStr, String address, String phone, String speciality, int experience) throws Exception {
        if (!map.checkAddress(address)) {
            throw new IllegalArgumentException("Адреса не входить до району обслуговування");
        }

        this.fullName = new FirstLastFatherName(fullNameStr);
        this.phoneNumber = new PhoneNumber(phone);
        if (!specialityList.checkSpeciality(speciality)) {
            throw new IllegalArgumentException("Невірна спеціальність");
        }
        this.workBook = new WorkBook(experience);

        LocalDate birthDate = LocalDate.parse(birthDateStr);
        registrationRecord.saveUnemployed(fullNameStr, birthDate, address, phone, speciality, experience);
    }
}