package za.co.umlandeli.model;

public class ProfilePOJO {


    private String FName, LName, EmailAddress, province, school_name, Uid, subj_1,subj_2, subj_3, subj_4, subj_5, subj_6, subj_7;
    private Integer grade;
    public ProfilePOJO() {
    }

    public ProfilePOJO(String FName, String LName, String emailAddress, String province, String school_name, String uid, String subj_1, String subj_2, String subj_3, String subj_4, String subj_5, String subj_6, String subj_7, Integer grade) {
        this.FName = FName;
        this.LName = LName;
        EmailAddress = emailAddress;
        this.province = province;
        this.school_name = school_name;
        Uid = uid;
        this.subj_1 = subj_1;
        this.subj_2 = subj_2;
        this.subj_3 = subj_3;
        this.subj_4 = subj_4;
        this.subj_5 = subj_5;
        this.subj_6 = subj_6;
        this.subj_7 = subj_7;
        this.grade = grade;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getSubj_1() {
        return subj_1;
    }

    public void setSubj_1(String subj_1) {
        this.subj_1 = subj_1;
    }

    public String getSubj_2() {
        return subj_2;
    }

    public void setSubj_2(String subj_2) {
        this.subj_2 = subj_2;
    }

    public String getSubj_3() {
        return subj_3;
    }

    public void setSubj_3(String subj_3) {
        this.subj_3 = subj_3;
    }

    public String getSubj_4() {
        return subj_4;
    }

    public void setSubj_4(String subj_4) {
        this.subj_4 = subj_4;
    }

    public String getSubj_5() {
        return subj_5;
    }

    public void setSubj_5(String subj_5) {
        this.subj_5 = subj_5;
    }

    public String getSubj_6() {
        return subj_6;
    }

    public void setSubj_6(String subj_6) {
        this.subj_6 = subj_6;
    }

    public String getSubj_7() {
        return subj_7;
    }

    public void setSubj_7(String subj_7) {
        this.subj_7 = subj_7;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
