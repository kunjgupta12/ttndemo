package com.ttn.demo.core.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component(
        service = Servlet.class,
        property = {
                ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=sling/servlet/default",
                ServletResolverConstants.SLING_SERVLET_SELECTORS + "=listchildren",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json",
                "sling.servlet.methods=GET"
        }
)
@ServiceDescription("List Direct Child Pages with Creation Date")
@ServiceVendor("Example Vendor")
public class ListChildPagesServlet extends SlingAllMethodsServlet {

    private static final String PARENT_PATH = "/content/we-retail/language-masters/en";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource parentResource = resourceResolver.getResource(PARENT_PATH);

        if (parentResource == null) {
            response.getWriter().write("{\"error\":\"Parent page not found\"}");
            return;
        }

        List<JsonObject> pagesList = new ArrayList<>();

        for (Resource child : parentResource.getChildren()) {
            Node node = child.adaptTo(Node.class);
            if (node != null) {
                try {
                    if (node.hasProperty("jcr:created")) {
                        Value createdDate = node.getProperty("jcr:created").getValue();

                        JsonObject pageJson = new JsonObject();
                        pageJson.addProperty("title", node.getName());
                        pageJson.addProperty("path", child.getPath());
                        pageJson.addProperty("createdDate", createdDate.getString());

                        pagesList.add(pageJson);
                    }
                } catch (RepositoryException e) {
                    response.getWriter().write("{\"error\":\"Error reading node properties\"}");
                    return;
                }
            }
        }

        // Sorting based on the request parameter
        String sortOrder = request.getParameter("sort");
        if ("desc".equalsIgnoreCase(sortOrder)) {
            pagesList.sort(Comparator.comparing(o -> o.get("createdDate").getAsString(), Collections.reverseOrder()));
        } else {
            pagesList.sort(Comparator.comparing(o -> o.get("createdDate").getAsString()));
        }

        JsonArray jsonArray = new JsonArray();
        pagesList.forEach(jsonArray::add);

        response.getWriter().write(jsonArray.toString());
    }
}
