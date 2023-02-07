package com.example.student_management;

public class Student {
    private String name;
    private int age;
    private String code;
    private double gpa;
    private int id;

    public Student(int id, String name, int age, String code,double gpa) {
        this.name = name;
        this.age = age;
        this.code = code;
        this.gpa = gpa;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String programCode) {
        this.code = code;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }


    public int getId() {
        return id;
    }



    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", programCode='" + code + '\'' +
                ", gpa='" + gpa + '\'' +
                ", id=" + id +
                '}';
    }

}