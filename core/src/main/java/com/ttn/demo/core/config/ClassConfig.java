package com.ttn.demo.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Class Configuration")
public @interface ClassConfig {

    @AttributeDefinition(name = "Max Students Allowed", description = "Set the max number of students in class")
    int max_students() default 30;

    @AttributeDefinition(name = "Passing Marks", description = "Set the passing marks for students")
    int passing_marks() default 40;
}

