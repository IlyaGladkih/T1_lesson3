package ru.test.RequestLoggingStarter.interseptors;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final String loggingLevel;

    private final String requestLoggerMessage;

    private final String responseLoggerMessage;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        LogManager.getLogger(LoggingInterceptor.class).log(
                Level.getLevel(loggingLevel),
                requestLoggerMessage,
                request.getMethod(),
                request.getURI(),
                request.getHeaders());

        ClientHttpResponse response = execution.execute(request, body);

        LogManager.getLogger(LoggingInterceptor.class).log(
                Level.getLevel(loggingLevel),
                responseLoggerMessage,
                response.getStatusCode(),
                response.getHeaders());

        return response;
    }
}
