package com.ttn.demo.core.models;

import java.util.List;

public interface FooterModel {
    String getTitle();
    String getFooterClass();
List<String> getNavTitles();
String getNavLink();
    interface NavItem {
        String getNavTitle();
        String getNavLink();
    }
}
