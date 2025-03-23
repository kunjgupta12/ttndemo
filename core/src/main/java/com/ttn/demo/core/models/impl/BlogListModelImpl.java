package com.ttn.demo.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.ttn.demo.core.models.BlogListModel;
import com.ttn.demo.core.services.BlogConfigService;
import com.ttn.demo.core.util.UtilClass;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class, adapters = BlogListModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BlogListModelImpl implements BlogListModel {


    @ScriptVariable
    private PageManager pageManager;

    @OSGiService
    private BlogConfigService blogConfigService;

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String parentPath; // Fetching path from dialog

    @Override
    public List<BlogItem> getBlogItems() {
        List<BlogItem> blogs = new ArrayList<>();
        UtilClass utilClass=new UtilClass();
        if (parentPath == null || parentPath.isEmpty()) {
            return blogs;
        }

        Page parentPage = pageManager.getPage(parentPath);
        if (parentPage == null) {
            return blogs;
        }

        String requestDate = request.getParameter("date");
        boolean filterByDate = requestDate != null && !requestDate.isEmpty();

        Iterator<Page> childPages = parentPage.listChildren();
        int count = 0;
        int maxItems = blogConfigService.getMaxItems(); // Fallback value

        while (childPages.hasNext() && count < maxItems) {
            Page childPage = childPages.next();
            String formattedDate = utilClass.getFormattedCreationDate(childPage,"MMMM yyyy");

            // Apply filter if requestDate is present
            if (filterByDate && !utilClass.matchesRequestedDate(formattedDate, requestDate)) {
                continue; // Skip blogs that don't match the requested date
            }

            String title = childPage.getTitle();
            String description = childPage.getProperties().get("jcr:description", "No Description");
            String link = childPage.getPath() + ".html";
            String imagePath =utilClass.getImagePath(childPage);

            blogs.add(new BlogItem(title, formattedDate, description, link, imagePath));
            count++;
        }

        return blogs;
    }


   }
