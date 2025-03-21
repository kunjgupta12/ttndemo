package com.ttn.demo.core.services.impl;

import com.ttn.demo.core.config.ClassConfig;
import com.ttn.demo.core.services.ClassConfigurationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import java.util.List;

@Component(service = ClassConfigurationService.class, immediate = true)
@Designate(ocd = ClassConfig.class)
public class ClassConfigurationServiceImpl implements ClassConfigurationService {

    private int maxStudents;
    private int passingMarks;

    @Activate
    @Modified
    protected void activate(ClassConfig config) {
        this.maxStudents = config.max_students();
        this.passingMarks = config.passing_marks();
    }

    @Override
    public boolean isClassLimitReached(List<?> students) {
        return students.size() >= maxStudents;
    }

    @Override
    public int getPassingMarks() {
        return passingMarks;
    }
}

