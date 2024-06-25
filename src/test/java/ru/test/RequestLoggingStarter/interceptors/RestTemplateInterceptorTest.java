package ru.test.RequestLoggingStarter.interceptors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import ru.test.RequestLoggingStarter.interseptors.RestTemplateLoggingInterceptor;

import java.io.IOException;

public class RestTemplateInterceptorTest {


    private RestTemplateLoggingInterceptor interceptor;

    @BeforeEach
    public void setup() throws Exception {
        interceptor = new RestTemplateLoggingInterceptor(
                "info",
                "Отправлен HTTP запрос метод {} , url запроса {}, Заголовки запроса: {}",
                "Получен HTTP ответ, код ответа {}, Заголовки ответа: {}"
        );
    }

    @Test
    public void testExecuteInvoke() throws IOException {

        ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);

        HttpRequest request = Mockito.mock(HttpRequest.class);

        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);

        Mockito.when(execution.execute(Mockito.any(),Mockito.any()))
                .thenReturn(response);


        ClientHttpResponse clientResponse = interceptor.intercept(request, new byte[0], execution);


        Assertions.assertEquals(clientResponse, response);

    }
}
