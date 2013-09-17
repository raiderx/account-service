package org.accountservice.server.web;

import org.accountservice.server.model.Statistics;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Pavel Karpukhin
 */
public class StatisticsFilter implements Filter {

    public static final String STATISTICS_ATTR = "STATISTICS";

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
        config.getServletContext().setAttribute(STATISTICS_ATTR, new Statistics());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        Statistics stat = (Statistics)config.getServletContext().getAttribute(STATISTICS_ATTR);
        stat.increase();
    }

    @Override
    public void destroy() {
        config.getServletContext().removeAttribute(STATISTICS_ATTR);
    }
}
