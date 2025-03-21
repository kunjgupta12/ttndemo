package com.ttn.demo.core.models.impl;

import com.adobe.granite.security.user.UserProperties;
import com.adobe.granite.security.user.UserPropertiesManager;
import com.adobe.granite.security.user.UserPropertiesService;
import com.ttn.demo.core.models.HeadingModel;
import com.ttn.demo.core.util.UtilClass;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import javax.annotation.PostConstruct;
import javax.jcr.Session;
import java.text.SimpleDateFormat;
import java.util.Date;

@Model(adapters = HeadingModel.class,
        adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeadingModelImpl implements HeadingModel {


    @ScriptVariable
    private PageManager pageManager;

    @SlingObject
    private ResourceResolver resourceResolver;

    @Self
    private SlingHttpServletRequest request;

    private String title;
    private String author;
    private String date;

    @PostConstruct
    protected void init() {
        UtilClass utilClass=new UtilClass();
        // Fetch current page
        Page currentPage = utilClass.getCurrentPage(pageManager,request);
        if (currentPage != null) {
            this.title = currentPage.getTitle();

            // Fetch created date properly
            this.date = utilClass.getFormattedCreationDate(currentPage,"MMM dd, yyyy");
        }

        // Fetch logged-in user author name
        this.author =utilClass.getLoggedInUserName(resourceResolver);
    }


@Override
    public String getTitle() {
        return title;
    }
@Override
    public String getAuthor() {
        return author;
    }
@Override
    public String getDate() {
        return date;
    }
}
