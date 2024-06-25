package ru.test.RequestLoggingStarter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.test.RequestLoggingStarter.interseptors.LoggingInterceptor;
import ru.test.RequestLoggingStarter.interseptors.RestTemplateLoggingInterceptor;

import java.util.List;


@AutoConfiguration
@EnableConfigurationProperties(InterceptorProperties.class)
@RequiredArgsConstructor
public class InterceptorRegistryConfig implements WebMvcConfigurer {

    private final InterceptorProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( loggingInterceptor() );
    }


    @Bean
    @ConditionalOnMissingBean
    public LoggingInterceptor loggingInterceptor(){
        return new LoggingInterceptor(properties.getLevel(),
                properties.getRequestLoggerMessage(),
                properties.getResponseLoggerMessage());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of(restTemplateLoggingInterceptor()));
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateLoggingInterceptor restTemplateLoggingInterceptor(){
        return new RestTemplateLoggingInterceptor(properties.getLevel(),
                properties.getRestTemplateRequestLoggerMessage(),
                properties.getRestTemplateResponseLoggerMessage());
    }


}
