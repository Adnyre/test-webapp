package adnyre.testweb;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

/**
 * Created by andrii.novikov on 04.10.2016.
 */
@WebServlet("/test/testB")
public class ServletB extends HttpServlet {

    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("HELLO WORLD! Servlet B");
    }
}