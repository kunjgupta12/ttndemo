package com.ttn.demo.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.ttn.demo.core.models.About;
import com.ttn.demo.core.util.UtilClass;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import javax.annotation.PostConstruct;

@Model(adapters = About.class,
        adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AboutModel implements About {
    @ScriptVariable
    private PageManager pageManager;

    @Self
    private SlingHttpServletRequest request;


    private String about;

    @PostConstruct
    protected void init() {
        UtilClass utilClass=new UtilClass();
        // Fetch current page
        Page currentPage = utilClass.getCurrentPage(pageManager,request);
        if (currentPage != null) {
            about = currentPage.getDescription();
        }
    }
@Override
    public String getAbout() {
        return about != null ? about : "No description available"; // Fallback for null
    }
}
