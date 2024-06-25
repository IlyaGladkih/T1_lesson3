package ru.test.RequestLoggingStarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "interceptor.logging")
public class InterceptorProperties {
    private String level = "info";
    private String requestLoggerMessage = "Получен HTTP запрос метод {} , url запроса {}, время обработки запроса {} мс, Заголовки запроса: {}";
    private String responseLoggerMessage = "Отправлен HTTP ответ, код {}, Заголовки ответа: {}";
    private String restTemplateRequestLoggerMessage = "Отправлен HTTP запрос метод {} , url запроса {}, Заголовки запроса: {}";
    private String restTemplateResponseLoggerMessage = "Получен HTTP ответ, код ответа {}, Заголовки ответа: {}";
}
