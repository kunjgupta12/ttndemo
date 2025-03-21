package com.ttn.demo.core.models.impl;

import com.ttn.demo.core.models.FooterModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Arrays;
import java.util.List;

@Model(
        adaptables = Resource.class,
        adapters = FooterModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FooterModelImpl implements FooterModel {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String footerClass;

    @ValueMapValue
    private String navLink;

    @ValueMapValue
    private String[] navTitle; // To handle multiple titles

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getFooterClass() {
        return footerClass;
    }

    public String getNavLink() {
        return navLink;
    }

    @Override
    public List<String> getNavTitles() {
        return navTitle != null ? Arrays.asList(navTitle) : null;
    }
}
