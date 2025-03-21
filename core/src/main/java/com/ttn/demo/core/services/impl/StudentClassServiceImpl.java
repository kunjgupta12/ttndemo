package com.ttn.demo.core.services.impl;

import com.ttn.demo.core.models.Student;
import com.ttn.demo.core.services.ClassConfigurationService;
import com.ttn.demo.core.services.StudentClassService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component(service = StudentClassService.class, immediate = true)
public class StudentClassServiceImpl implements StudentClassService {

    private final List<Student> students = new ArrayList<>();

    @Reference

    ClassConfigurationService classConfigService;

    @Override
    public boolean addStudent(Student student) {
        if (classConfigService.isClassLimitReached(students)) {
            return false;
        }
        return students.add(student);
    }

    @Override
    public boolean deleteStudent(int id) {
        return students.removeIf(student -> student.getId() == id);
    }

    @Override
    public boolean isStudentPassed(int id) {
        return getStudent(id) != null && getStudent(id).getMarks() >= classConfigService.getPassingMarks();
    }

    @Override
    public Student getStudent(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}

