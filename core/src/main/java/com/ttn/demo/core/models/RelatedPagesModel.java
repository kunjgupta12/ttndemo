package com.ttn.demo.core.models;

import com.adobe.cq.wcm.core.components.models.Page;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = RelatedPagesModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RelatedPagesModel {

 /*   @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String namespace;

    @ValueMapValue
    private String[] additionalTags;

    private List<Page> relatedPages = new ArrayList<>();

    @PostConstruct
    protected void init() {
        ResourceResolver resourceResolver = request.getResourceResolver();
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

        if (tagManager == null) {
            return;
        }

        // Get current page
        Page currentPage = request.adaptTo(Page.class);
        if (currentPage == null) {
            return;
        }

        // Get tags from current page
        Tag[] currentPageTags = tagManager.getTags(currentPage.getContentResource());

        // Convert additionalTags into Tag objects
        List<Tag> additionalTagList = new ArrayList<>();
        for (String tagPath : additionalTags) {
            Tag tag = tagManager.resolve(tagPath);
            if (tag != null) {
                additionalTagList.add(tag);
            }
        }

        // Retrieve all pages in the content tree and filter them
        Iterable<Resource> allPages = (Iterable<Resource>) resourceResolver.findResources("SELECT * FROM [cq:Page]", "JCR-SQL2");

        for (Resource pageResource : allPages) {
            Page page = pageResource.adaptTo(Page.class);
            if (page != null && !page.getPath().equals(currentPage.getPath())) {
                Tag[] pageTags = tagManager.getTags(page.getContentResource());

                // Check if page contains any additional tags OR shares a namespace tag with current page
                boolean matchesAdditionalTags = containsTag(pageTags, additionalTagList);
                boolean matchesNamespaceTags = containsNamespaceTag(pageTags, currentPageTags, namespace);

                if (matchesAdditionalTags || matchesNamespaceTags) {
                    relatedPages.add(page);
                }
            }
        }
    }

    public List<Page> getRelatedPages() {
        return relatedPages;
    }

    private boolean containsTag(Tag[] pageTags, List<Tag> additionalTags) {
        for (Tag tag : pageTags) {
            if (additionalTags.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsNamespaceTag(Tag[] pageTags, Tag[] currentPageTags, String namespace) {
        List<Tag> currentTags = List.of(currentPageTags);
        for (Tag tag : pageTags) {
            if (tag.getNamespace().equals(namespace) && currentTags.contains(tag)) {
                return true;
            }
        }
        return false;
    }*/
}
