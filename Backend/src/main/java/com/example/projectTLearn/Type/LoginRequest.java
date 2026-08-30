package com.example.projectTLearn.Type;

public class LoginRequest {

    private String studentCode;

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    private String passWord;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public LoginRequest() {
    }

    public LoginRequest(String studentCode, String passWord) {
        this.studentCode = studentCode;
        this.passWord = passWord;
    }

}
