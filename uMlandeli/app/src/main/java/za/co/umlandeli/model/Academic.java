package za.co.umlandeli.model;

public class Academic {
    private String subject1, subject2, subject3, subject4, subject5, subject6, subject7;
    private Integer exercises, subj1_points, subj2_points, subj3_points, subj4_points, subj5_points, subj6_points, subj7_points, written;

    public Academic() {
    }

    public Academic(String subject1, String subject2, String subject3, String subject4, String subject5, String subject6, String subject7, Integer exercises, Integer subj1_points, Integer subj2_points, Integer subj3_points, Integer subj4_points, Integer subj5_points, Integer subj6_points, Integer subj7_points, Integer written) {
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
        this.subject4 = subject4;
        this.subject5 = subject5;
        this.subject6 = subject6;
        this.subject7 = subject7;
        this.exercises = exercises;
        this.subj1_points = subj1_points;
        this.subj2_points = subj2_points;
        this.subj3_points = subj3_points;
        this.subj4_points = subj4_points;
        this.subj5_points = subj5_points;
        this.subj6_points = subj6_points;
        this.subj7_points = subj7_points;
        this.written = written;
    }

    public Integer getSubj6_points() {
        return subj6_points;
    }

    public void setSubj6_points(Integer subj6_points) {
        this.subj6_points = subj6_points;
    }

    public Integer getSubj7_points() {
        return subj7_points;
    }

    public void setSubj7_points(Integer subj7_points) {
        this.subj7_points = subj7_points;
    }

    public Integer getWritten() {
        return written;
    }

    public void setWritten(Integer written) {
        this.written = written;
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

    public Integer getExercises() {
        return exercises;
    }

    public void setExercises(Integer exercises) {
        this.exercises = exercises;
    }

    public Integer getSubj1_points() {
        return subj1_points;
    }

    public void setSubj1_points(Integer subj1_points) {
        this.subj1_points = subj1_points;
    }

    public Integer getSubj2_points() {
        return subj2_points;
    }

    public void setSubj2_points(Integer subj2_points) {
        this.subj2_points = subj2_points;
    }

    public Integer getSubj3_points() {
        return subj3_points;
    }

    public void setSubj3_points(Integer subj3_points) {
        this.subj3_points = subj3_points;
    }

    public Integer getSubj4_points() {
        return subj4_points;
    }

    public void setSubj4_points(Integer subj4_points) {
        this.subj4_points = subj4_points;
    }

    public Integer getSubj5_points() {
        return subj5_points;
    }

    public void setSubj5_points(Integer subj5_points) {
        this.subj5_points = subj5_points;
    }
}