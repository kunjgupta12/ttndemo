package com.ttn.demo.core.models;

public class Student {
    private int id;
    private String name;
    private int marks;
    private int age;

    public Student(int id, String name, int marks, int age) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getMarks() { return marks; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', marks=" + marks + ", age=" + age + "}";
    }
}