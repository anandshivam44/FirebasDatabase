package com.example.firebasedatabase.model;

public class User {
    String Name;
    String age;

    public User(String Name, String age) {
        this.Name = Name;
        this.age = age;
    }

    public User() {
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
