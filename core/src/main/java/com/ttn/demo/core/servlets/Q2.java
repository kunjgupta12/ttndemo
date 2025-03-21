package com.ttn.demo.core.servlets;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.annotation.Nonnull;
import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class, name = "Demo Servlet POST", property = {
        org.osgi.framework.Constants.SERVICE_DESCRIPTION + "=Demo Servlet POST for Bootcamp",
        "sling.servlet.resourceTypes=" + "ttndemo/api/demo",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.selectors=" + "testPage",
        "sling.servlet.extensions=" + "json"

})
public class Q2 extends SlingAllMethodsServlet {
    @Override
    protected void doPost(@Nonnull final SlingHttpServletRequest request, @Nonnull final SlingHttpServletResponse response) throws IOException {
        String pageName = request.getParameter("pageName");
        String parentPath = request.getParameter("parentPath");

        // Get the ResourceResolver
        ResourceResolver resolver = request.getResourceResolver();

        // Get the parent resource where the page will be created
        Resource parentResource = resolver.getResource(parentPath);

        try {
            // Create the page (under the specified parent path) with the specified template
            String pagePath = parentPath + "/" + pageName;

            // Create the page node (you can use the correct node type for pages in AEM)
            Resource pageResource = resolver.create(parentResource, pageName, null);  // Create the empty page node

            // Commit the changes to the repository
            resolver.commit();

            // Return the success response
            response.setStatus(SlingHttpServletResponse.SC_CREATED);
            response.getWriter().write("Page created successfully at " + pagePath);

        } catch (Exception e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error creating page: " + e.getMessage());
        }
    }
}

