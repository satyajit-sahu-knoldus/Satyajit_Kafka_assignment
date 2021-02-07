package com.satyajit.model;

public class User {
    private int id;
    private String name;
    private int age;
    private String course;

    public User() {
    }

    //create a user function to get the values
    public User(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    //fetch the id and return it
    public int getId() {
        return this.id;
    }

    //fetch the name and return it
    public String getName() {

        return this.name;
    }

    //fetch the age and return it
    public int getAge() {

        return this.age;
    }

    //fetch the course and return it
    public String getCourse() {
        return this.course;
    }

    @Override
    //creating the format to return the values
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", course='" + course + '\'' +
                '}';
    }
}
