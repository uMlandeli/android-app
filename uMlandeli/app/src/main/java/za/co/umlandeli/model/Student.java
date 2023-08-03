package za.co.umlandeli.model;

public class Student{
    private String fullName, surname, email;

    public Student() {
    }

    public Student(String fullName, String surname, String email) {
        this.fullName = fullName;
        this.surname = surname;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
