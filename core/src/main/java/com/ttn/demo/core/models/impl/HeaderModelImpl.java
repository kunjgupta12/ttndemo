package com.ttn.demo.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.ttn.demo.core.models.HeaderModel;
import com.ttn.demo.core.util.UtilClass;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = HeaderModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModelImpl implements HeaderModel {

    @ValueMapValue
    @Default(values = "Default Title")
    private String logoText;

    @ValueMapValue
    @Default(values = "Default Image")
    private String logoImage;

    @ValueMapValue(name = "navTitle")
    private List<String> navTitle;

    @ValueMapValue(name = "navLink")
    private List<String> navLink;

    @Self
    private SlingHttpServletRequest request;

    private List<NavItems> navItems;
    private List<String> isOpen;

    @Override
    public String getLogoImage() {
        return logoImage;
    }

    @Override
    public String getLogoText() {
        return logoText;
    }

    @PostConstruct
    protected void init() {
        navItems = new ArrayList<>();
        isOpen = new ArrayList<>();
        ResourceResolver resourceResolver = request.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        if (navTitle != null && navLink != null && pageManager != null) {
            for (int i = 0; i < navTitle.size(); i++) {
                Resource resource = resourceResolver.getResource(navLink.get(i));
                if (resource != null) {
                    Page page =
                            pageManager.getContainingPage(resource);

                    String hideInNav =
                            page.getProperties().get("hideInNav", "false");


                    isOpen.add(hideInNav);
                    navItems.add(new NavItems(navTitle.get(i), navLink.get(i), hideInNav));
                }
            }
        }
    }

    @Override
    public List<NavItems> getNavItems() {
        return navItems;
    }

}
