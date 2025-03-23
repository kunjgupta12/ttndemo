package com.ttn.demo.core.util;

import com.adobe.granite.security.user.UserProperties;
import com.adobe.granite.security.user.UserPropertiesManager;
import com.adobe.granite.security.user.UserPropertiesService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import javax.jcr.Session;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilClass {
        public String getFormattedCreationDate(Page page,String pattern) {
            Date createdDate = page.getProperties().get("jcr:created", Date.class);
            if (createdDate != null) {
                SimpleDateFormat outputFormat = new SimpleDateFormat(pattern);
                return outputFormat.format(createdDate);
            }
        return null;
    }
    public String getLoggedInUserName(ResourceResolver resourceResolver) {
        try {
            Session session = resourceResolver.adaptTo(Session.class);
            if (session != null) {
                String userId = session.getUserID();
                UserPropertiesManager upm = resourceResolver.adaptTo(UserPropertiesManager.class);
                if (upm != null) {
                    UserProperties userProperties = upm.getUserProperties(userId, UserPropertiesService.PROFILE_PATH);
                    if (userProperties != null) {
                        // Fetch full name, if available; otherwise, fallback to userId
                        String fullName = userProperties.getProperty("profile/fullName");
                        return (fullName != null && !fullName.isEmpty()) ? fullName : userId;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }
    public  Page getCurrentPage(PageManager pageManager, SlingHttpServletRequest request){
        return pageManager.getContainingPage(request.getResource());
    }
    public boolean matchesRequestedDate(String formattedDate, String requestDate) {
         final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

        try {
            Date blogDate =
                    DATE_FORMAT.parse(formattedDate); // Convert blog date to Date object
            String blogMonthYear = DATE_FORMAT.format(blogDate); // Convert to "MMMM yyyy" format
            return blogMonthYear.equals(requestDate);
        } catch (Exception e) {
            return false;
        }
    }
    public  String getImagePath(SlingHttpServletRequest request, Page page) {
        String ImagePath = page.getPath() + "/jcr:content/cq:featuredimage/file/jcr:content";
        ResourceResolver resolver = request.getResourceResolver();
        Resource ImageNode = resolver.getResource(ImagePath);
        String ImageLink = "";
        try {
            ValueMap properties = ImageNode.getValueMap();
            if (properties.containsKey("jcr:data")) { // Check if binary data exists
                ImageLink = resolver.map(request, ImagePath) + "/jcr:data";
            }
        } catch (Exception e) {
            return "{\"message\" : \" Could not get Banner Image from the Banner Component of Page\"";
        }
        return ImageLink;
    }

}

