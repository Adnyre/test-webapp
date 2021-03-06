package adnyre.controller;

import adnyre.exceptions.UserAlreadyExistsException;
import adnyre.exceptions.UserNotFoundException;
import adnyre.model.User;
import adnyre.service.UserService;
import adnyre.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService service = new UserServiceImpl();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            try {
                User user = service.getUserById(Integer.parseInt(id));
                response.getWriter().write("<h2>Information about the requested user:</h2><br>");
                response.getWriter().write("First name: " + user.getFirstName() + "<br>");
                response.getWriter().write("Last name: " + user.getLastName());
            } catch (UserNotFoundException e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No such user found.");
            }
        } else {
            response.getWriter().write("<h2>Information about all users:</h2><br>");
            List<User> userList = service.getAllUsers();
            for (User user : userList) {
                response.getWriter().write("First name: " + user.getFirstName() + ", last name: " + user.getLastName() + "<br>");
            }
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String idStr = request.getParameter("id");
        int id = (idStr != null && !idStr.isEmpty()) ? Integer.valueOf(idStr) : 0;
        User user = new User(id, firstName, lastName);
        try {
            service.saveOrUpdateUser(user);
            response.getWriter().write("<h2>Database updated.</h2>");
        } catch (UserAlreadyExistsException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unable to save user data: there already exists a user " +
                    "with such a name in the database");
        }
    }

    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            service.deleteUser(Integer.parseInt(request.getParameter("id")));
            response.getWriter().write("<h2>User information deleted from the database.</h2>");
        } catch (UserNotFoundException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No such user found.");
        }
    }
}
