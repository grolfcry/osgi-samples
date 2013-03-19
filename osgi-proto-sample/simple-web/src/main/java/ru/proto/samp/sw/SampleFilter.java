package ru.proto.samp.sw;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * User: fmv
 * Date: 19.03.13
 * Time: 1:09
 */
@WebFilter(value = "/*")
public class SampleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("SampleFilter init, "+filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter: sr="+servletRequest+",sresp="+servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("SampleFilter destroy");
    }
}
