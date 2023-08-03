package za.co.umlandeli.model;

public class ProfilePOJO {

    private Academic academic;
    private Student student;

    public ProfilePOJO() {
    }

    public ProfilePOJO(Academic academic, Student student) {
        this.academic = academic;
        this.student = student;
    }

    public Academic getAcademic() {
        return academic;
    }

    public void setAcademic(Academic academic) {
        this.academic = academic;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
