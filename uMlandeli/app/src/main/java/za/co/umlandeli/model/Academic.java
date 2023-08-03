package za.co.umlandeli.model;

public class Academic{
    private String schoolProvince, schoolName, subject1,subject2, subject3, subject4, subject5, subject6, subject7;
    private Integer grade;
    public Academic() {
    }

    public Academic(String schoolProvince, String schoolName, String subject1, String subject2, String subject3, String subject4, String subject5, String subject6, String subject7, Integer grade) {

        this.schoolProvince = schoolProvince;
        this.schoolName = schoolName;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
        this.subject4 = subject4;
        this.subject5 = subject5;
        this.subject6 = subject6;
        this.subject7 = subject7;
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getSchoolProvince() {
        return schoolProvince;
    }

    public void setSchoolProvince(String schoolProvince) {
        this.schoolProvince = schoolProvince;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getSubject4() {
        return subject4;
    }

    public void setSubject4(String subject4) {
        this.subject4 = subject4;
    }

    public String getSubject5() {
        return subject5;
    }

    public void setSubject5(String subject5) {
        this.subject5 = subject5;
    }

    public String getSubject6() {
        return subject6;
    }

    public void setSubject6(String subject6) {
        this.subject6 = subject6;
    }

    public String getSubject7() {
        return subject7;
    }

    public void setSubject7(String subject7) {
        this.subject7 = subject7;
    }
}
