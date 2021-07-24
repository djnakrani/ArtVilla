package com.example.artvilla;

import java.util.PrimitiveIterator;

public class User {

    private String U_id;
    private String Name;
    private String Mobile;
    private String Mail;
    private String UserType;

    User()
    {
    }

    public User(String u_id, String name, String mobile, String mail, String imageURL, String userType) {
        U_id = u_id;
        Name = name;
        Mobile = mobile;
        Mail = mail;
        UserType = userType;
    }

    public String getU_id() {
        return U_id;
    }

    public void setU_id(String u_id) {
        U_id = u_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
