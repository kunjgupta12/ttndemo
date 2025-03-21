//package com.ttn.demo.core.servlets;
//
//
//import com.google.gson.Gson;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.SlingHttpServletResponse;
//import org.apache.sling.api.servlets.SlingAllMethodsServlet;
//import org.apache.sling.servlets.annotations.SlingServletPaths;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.propertytypes.ServiceDescription;
//
//import javax.servlet.Servlet;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component(service = Servlet.class)
//@SlingServletPaths(value={"/bin/users"})
//@ServiceDescription("Sample servlet to demonstrate crud operation")
//public class Demo extends SlingAllMethodsServlet { //SlingSafeMethodsServlet   SlingAllMethodsServlet
//
//    List<Employee> employees=new ArrayList<>();
//    @Override
//    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
//        String body=   readRequestBody(request);
//        Gson gson = new Gson();
//        // Convert the JSON string to an Employee object
//        Employee emp = gson.fromJson(body, Employee.class);
//        employees.add(emp);
//    }
//
//    @Override
//    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
//        employees.add(new Employee(1,"Emp1","SE",5000));
//        employees.add(new Employee(2,"Emp2","SSE",7000));
//        employees.add(new Employee(3,"Emp3","SSE",7000));
//        employees.add(new Employee(4,"Emp4","ATL",8000));
//        employees.add(new Employee(5,"Emp5","TL",9000));
//        String requestParamId=request.getParameter("id");
//        if(requestParamId==null) {
//            response.getWriter().write(new Gson().toJson(employees));
//        }else{
//            if(isNumeric(requestParamId)) {
//
//                response.getWriter().write(new Gson().toJson(employees.get(Integer.parseInt(requestParamId))));
//            }else {
//                response.setStatus(400);
//            }
//
//        }
//    }
//    public static boolean isNumeric(String str) {
//        return str.matches("-?\\d+(\\.\\d+)?");
//    }
//
//    private String readRequestBody(SlingHttpServletRequest request) throws IOException {
//        // Read the request body (JSON data)
//        StringBuilder sb = new StringBuilder();
//        String line;
//        try (BufferedReader reader = request.getReader()) { // Directly use getReader() without InputStreamReader
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }
//        return sb.toString();
//    }
//}