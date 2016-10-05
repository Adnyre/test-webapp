package adnyre.testweb;

import adnyre.db.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by andrii.novikov on 05.10.2016.
 */
@WebServlet("/test/testDB")
public class DBAccessServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection con = DBHandler.getConnection();
            String query =
                    "select id as user_id, first_name as fn, last_name as ln from \"user\"";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            response.getWriter().write("<table border=1><tr><th>id</th><th>fist name</th><th>last name</th></tr>");
            while (rs.next()) {
                response.getWriter().write("<tr>");
                int userID = rs.getInt("user_id");
                String firstName = rs.getString("fn");
                String lastName = rs.getString("ln");
                response.getWriter().write("<td>" + userID + "</td><td>" + firstName + "</td><td>" +
                        lastName + "</td>");
                response.getWriter().write("</tr>");
            }
            response.getWriter().write("</table>");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}