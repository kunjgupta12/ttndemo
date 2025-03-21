package com.ttn.demo.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Blog List Configuration", description = "Configuration for displaying blogs")
public @interface BlogConfig {
    @AttributeDefinition(name = "Number of Blog Items", description = "Set the number of blogs to display")
    int maxItems() default 2;
}
