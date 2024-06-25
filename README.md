# Описание проекта

Проект представляет собой Spring Boot Starter для логирования HTTP запросов.  

После подключения стартер начинает входящие и исходящие в приложении. Логирование реализовано посредством интерцепторов Spring. В качестве логгера используется log4j.

# Конфигурация

## Уровень логирования

Уровень логирования по умолчанию info.

Изменить уровень логирования можно через application.properties (application.yml) исползуя ключ interceptor.logging.level.

Доступные уровни логирования: DEBUG, INFO, WARN, ERROR, FATAL, TRACE

## Формат сообщений

### Входящие HTTP запросы логируюся в формате: 

"Получен HTTP запрос метод {} , url запроса {}, время обработки запроса {} мс, Заголовки запроса: {}"  

Изменить формат сообщения можно исползуя ключ: interceptor.logging.request-logger-message.

### Ответы на запосы логируюся в формате: 

"Отправлен HTTP ответ, код {}, Заголовки ответа: {}"

Изменить формат сообщения можно исползуя ключ: interceptor.logging.response-logger-message.

### Запросы отправленные через RestTemplate логируюся в формате:

"Отправлен HTTP запрос метод {} , url запроса {}, Заголовки запроса: {}"

Изменить формат сообщения можно исползуя ключ: interceptor.rest-template-request-logger-message.

### Ответы на запросы отправленные через RestTemplate логируюся в формате:

"Получен HTTP ответ, код ответа {}, Заголовки ответа: {}"

Изменить формат сообщения можно исползуя ключ: interceptor.rest-template-response-logger-message.

# Подключение

Для подклчения стартера в своем проекте его необходимо предварительно скомпилировать посредством комманды:

```console
maven:install
```

Затем в своем проекте добавить в файле pom.xml зависмость:

```console
<dependency>
	<groupId>ru.test</groupId>
	<artifactId>Request-Logging-Starter</artifactId>
	<version>0.0.1</version>
</dependency>
```