package com.ttn.demo.core.services;

import com.ttn.demo.core.models.Student;
import java.util.List;

public interface StudentClassService {
    boolean addStudent(Student student);
    boolean deleteStudent(int id);
    boolean isStudentPassed(int id);
    Student getStudent(int id);
    List<Student> getAllStudents();
}

