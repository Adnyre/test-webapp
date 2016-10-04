package adnyre.testweb;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.util.*;

/**
 * Created by andrii.novikov on 04.10.2016.
 */
@WebFilter("/test")
public class TestFilter implements Filter  {
    public void  init(FilterConfig config){
    }
    public void  doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws java.io.IOException, ServletException {
        String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
        System.out.println("User-Agent: " + userAgent);
        chain.doFilter(request,response);
    }
    public void destroy( ){
      //?
    }
}