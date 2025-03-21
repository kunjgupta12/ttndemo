package com.ttn.demo.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.ttn.demo.core.models.ArchivesModel;
import com.ttn.demo.core.util.UtilClass;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Model(adapters = ArchivesModel.class,
        adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ArchivesModelImpl implements ArchivesModel {
    @ScriptVariable
    private PageManager pageManager;

    @Self
    private SlingHttpServletRequest request;

    private List<String> archiveDates;
String link;
    @PostConstruct
    protected void init() {
        // Use a Set to store unique months
        Set<String> uniqueMonths = new LinkedHashSet<>();
UtilClass utilClass=new UtilClass();
        // Get the current page
        Page currentPage = utilClass.getCurrentPage(pageManager,request);

        if (currentPage != null) {
            Page parentPage = currentPage.getParent();
link=currentPage.getParent().getPath()+".html";
            if (parentPage != null) {
                for (Iterator<Page> it = parentPage.listChildren(); it.hasNext(); ) {
                    Page childPage = it.next();
                    String formattedDate =utilClass
                    .getFormattedCreationDate(childPage,"MMMM yyyy");
                    if (formattedDate != null) {
                        uniqueMonths.add(formattedDate); // Ensures uniqueness
                    }
                }
            }
        }

        // Convert Set to List for the getter method
        archiveDates = new ArrayList<>(uniqueMonths);
    }


    public List<String> getArchiveDates() {
        return archiveDates;
    }
    public String getLink(){
        return  link;
    }
}
