package com.ttn.demo.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "",description = "")
public @interface Test {
    @AttributeDefinition(name = "",description = "")
    int student_no() default 2;

}



