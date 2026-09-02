package com.example.projectTLearn.Type;

public class LoginRequest {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String studentCode) {
        this.code = studentCode;
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

    public LoginRequest(String code, String passWord) {
        this.code = code;
        this.passWord = passWord;
    }

}
