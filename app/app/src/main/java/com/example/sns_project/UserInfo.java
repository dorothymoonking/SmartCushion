package com.example.sns_project;

public class UserInfo {
    private String name;
    private String phoneNumber;
    private String age;
    private String gender;
    private String photoUrl;

    public UserInfo(String name, String phoneNumber, String age, String gender, String photoUrl){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.photoUrl = photoUrl;
    }

    public UserInfo(String name, String phoneNumber, String age, String gender){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getAge(){return this.age; }
    public void getAge(String birthDay){
        this.age = age;
    }
    public String getGender(){
        return this.gender;
    }
    public void setGender(String gender){this.gender = gender; }
    public String getPhotoUrl(){
        return this.photoUrl;
    }
    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
