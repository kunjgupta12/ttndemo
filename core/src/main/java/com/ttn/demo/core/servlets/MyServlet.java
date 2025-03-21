package com.ttn.demo.core.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.ttn.demo.core.models.Student;
import com.ttn.demo.core.services.ClassConfigurationService;
import com.ttn.demo.core.services.StudentClassService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/MyServlet",
                "sling.servlet.methods=GET,DELETE,POST"
        }
)
public class MyServlet extends SlingAllMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServlet.class);

    @Reference
    private StudentClassService studentService;
@Reference

    private ClassConfigurationService classConfigService;
    @Override
    protected void doDelete(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean deleted = studentService.deleteStudent(id);

            jsonResponse.put("message", deleted ? "Student deleted successfully!" : "Student not found.");

        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input: {}", e.getMessage());
            jsonResponse.put("error", "Invalid student ID.");
        } catch (Exception e) {
            LOGGER.error("Error processing request: {}", e.getMessage());
            jsonResponse.put("error", "Something went wrong. Check logs.");
        }

        resp.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        JSONObject jsonResponse = new JSONObject();

        try {
            if ("add".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                int marks = Integer.parseInt(req.getParameter("marks"));
                int age = Integer.parseInt(req.getParameter("age"));

                Student student = new Student(id, name, marks, age);
                boolean added = studentService.addStudent(student);

                jsonResponse.put("message", added ? "Student Added Successfully!" : "Class Limit Reached!");
            }

            else if ("getAll".equalsIgnoreCase(action)) {
                List<Student> students = studentService.getAllStudents();
                JSONArray studentArray = new JSONArray();

                for (Student s : students) {
                    JSONObject studentJson = new JSONObject();
                    studentJson.put("id", s.getId());
                    studentJson.put("name", s.getName());
                    studentJson.put("marks", s.getMarks());
                    studentJson.put("age", s.getAge());
                    studentArray.put(studentJson);
                }

                jsonResponse.put("students", studentArray);
            }

            else if ("isPassed".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean passed = studentService.isStudentPassed(id);
                jsonResponse.put("result", passed ? "Student Passed!" : "Student Failed!");
            }
            else if("passingMarks".equals(action)){ jsonResponse.put("passingMarks", classConfigService.getPassingMarks());}
         else if("classLimitReached".equals(action)) {
                jsonResponse.put("classLimitReached", classConfigService.isClassLimitReached(studentService.getAllStudents()));

            }     else {
                jsonResponse.put("error", "Invalid action parameter.");
            }

        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input: {}", e.getMessage());
            jsonResponse.put("error", "Invalid input parameters.");
        } catch (Exception e) {
            LOGGER.error("Error processing request: {}", e.getMessage());
            jsonResponse.put("error", "Something went wrong. Please check logs.");
        }

        resp.getWriter().write(jsonResponse.toString());
    }
}
