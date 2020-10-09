package com.library.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
public class LogFilter extends ZuulFilter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();

        log.info("**** Requête interceptée ! L'URL est : {} " , req.getRequestURL().append('?').append(req.getQueryString()));
        if ("POST".equalsIgnoreCase(req.getMethod()) || "PATCH".equalsIgnoreCase(req.getMethod()))
        {
            try {
                log.info(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Enumeration<String> headerNames = req.getHeaderNames();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = req.getHeader(headerName);
            String info = "Header Name: " + headerName + " Value: " + headerValue;
            log.info(info);
        }
        return null;
    }
}
