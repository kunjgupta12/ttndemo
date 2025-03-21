package com.ttn.demo.core.services;

import java.util.List;

public interface ClassConfigurationService {
    boolean isClassLimitReached(List<?> students);
    int getPassingMarks();
}
