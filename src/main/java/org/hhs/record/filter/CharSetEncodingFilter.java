package org.hhs.record.filter;


import javax.servlet.*;
import java.io.IOException;

public class CharSetEncodingFilter implements Filter {
    String charEncode = null;
    public void init(FilterConfig filterConfig) throws ServletException {
        charEncode = filterConfig.getInitParameter("charEncode");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(charEncode);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
