package adnyre.testweb;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by andrii.novikov on 04.10.2016.
 */
@WebFilter(urlPatterns="/test/*")
public class TestFilter implements Filter  {
    public void  init(FilterConfig config){
        System.out.println("Initializing TestFilter");
    }
    public void  doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws java.io.IOException, ServletException {
        response.setContentType("text/html");
        String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
        System.out.println("User-Agent: " + userAgent);
        chain.doFilter(request,response);
    }
    public void destroy( ){
        System.out.println("Destroying TestFilter");
    }
}