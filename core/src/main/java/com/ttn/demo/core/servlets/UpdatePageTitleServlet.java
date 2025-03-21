package com.ttn.demo.core.servlets;

import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(
        service = Servlet.class,
        property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/updatePageTitle",
                "sling.servlet.methods=POST"
        }
)
@ServiceDescription("Update Page Title Servlet")
@ServiceVendor("Example Vendor")
public class UpdatePageTitleServlet extends SlingAllMethodsServlet {

    private static final String BASE_PATH = "/content/we-retail/language-masters/en/";

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pageName = request.getParameter("pageName");
        String newTitle = request.getParameter("newTitle");

        JsonObject jsonResponse = new JsonObject();

        if (pageName == null || newTitle == null) {
            jsonResponse.addProperty("error", "Missing parameters. Required: pageName, newTitle.");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        // Correcting the path to target jcr:content
        String pageContentPath = BASE_PATH + pageName + "/jcr:content";

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource pageContentResource = resourceResolver.getResource(pageContentPath);

        if (pageContentResource == null) {
            jsonResponse.addProperty("error", "Page content node not found: " + pageContentPath);
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        try {
            ModifiableValueMap properties = pageContentResource.adaptTo(ModifiableValueMap.class);

            if (properties != null) {
                properties.put("jcr:title", newTitle);

                // Save changes
                Session session = resourceResolver.adaptTo(Session.class);
                if (session != null) {
                    session.save();
                }

                jsonResponse.addProperty("success", "Page title updated successfully.");
                jsonResponse.addProperty("pagePath", pageContentPath);
                jsonResponse.addProperty("newTitle", newTitle);
            } else {
                jsonResponse.addProperty("error", "Unable to modify the page content node.");
            }
        } catch (RepositoryException e) {
            jsonResponse.addProperty("error", "RepositoryException: " + e.getMessage());
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
