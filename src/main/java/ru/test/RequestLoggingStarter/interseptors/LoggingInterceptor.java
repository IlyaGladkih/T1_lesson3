package ru.test.RequestLoggingStarter.interseptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final String loggingLevel;

    private final String requestLoggerMessage;

    private final String responseLoggerMessage;

    private Logger logger = LogManager.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("timeControl",System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long requestTime = System.currentTimeMillis() - (long) request.getAttribute("timeControl");
        logger.log(
                Level.getLevel(loggingLevel),
                requestLoggerMessage,
                request.getMethod(),
                request.getRequestURL(),
                requestTime,
                getRequestHeaders(request));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.log(
                Level.getLevel(loggingLevel),
                responseLoggerMessage,
                response.getStatus(),
                getResponseHeaders(response)
                );
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key,value);
        }
        return map;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        for (String key : response.getHeaderNames()) {
            map.put(key, response.getHeader(key));
        }
        return map;
    }

}


