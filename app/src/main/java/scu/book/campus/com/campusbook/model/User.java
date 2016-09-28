package scu.book.campus.com.campusbook.model;


import java.io.Serializable;

/**
 * Created by kushahuja on 5/23/16.
 */
public class User implements Serializable {
    public String name;
    public String email;
    public String phoneNumber;
    public boolean isRegistered;

    public User(){}
    public User(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isRegistered = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber= phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email= email;
    }

    public String toString() {
        return name + ", " + email;
    }




}
