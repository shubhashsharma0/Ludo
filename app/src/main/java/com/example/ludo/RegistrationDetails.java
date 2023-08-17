package com.example.ludo;

public class RegistrationDetails {
    String name, gender, dob, email, password, confirmPassword;

    public RegistrationDetails() {
    }

    RegistrationDetails(String name, String gender, String dob, String email, String password, String confirmPassword) {
         this.name = name;
         this.gender = gender;
         this.dob = dob;
         this.email = email;
         this.password = password;
         this.confirmPassword = confirmPassword;
     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
