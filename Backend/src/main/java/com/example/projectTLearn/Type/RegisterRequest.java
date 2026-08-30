package com.example.projectTLearn.Type;

public class RegisterRequest {

    private String studentCode;
    private String passWord;
    private String name;
    private String fullName;
    private String phone;
    private String gender;

    public RegisterRequest() {
    }

    public RegisterRequest(String studentCode, String passWord, String name, String fullName, String phone,
            String gender) {
        this.studentCode = studentCode;
        this.passWord = passWord;
        this.name = name;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
