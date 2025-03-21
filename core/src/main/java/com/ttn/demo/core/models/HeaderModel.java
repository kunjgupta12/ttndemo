package com.ttn.demo.core.models;

import com.ttn.demo.core.models.impl.NavItems;

import java.util.List;

public interface HeaderModel {
    String getLogoImage();
    String getLogoText();
    List<NavItems> getNavItems();
    // Corrected to follow camelCase
}
