package ru.test.RequestLoggingStarter.interceptors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.RequestLoggingStarter.interseptors.LoggingInterceptor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class LoggingInterceptorTestController {


    @Mock
    private LoggingInterceptor interceptor;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(new TestController())
                .addInterceptors(interceptor)
                .build();
        Mockito.when(interceptor.preHandle(Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    public void testGetRequestExpectInvocationAllInterceptorMethodsReturningCodeIsOk() throws Exception {

        mockMvc.perform(get("/test")).andExpect(status().isOk());

        Mockito.verify(interceptor, Mockito.times(1))
                .preHandle(Mockito.any(),Mockito.any(), Mockito.any());
        Mockito.verify(interceptor, Mockito.times(1))
                .postHandle(Mockito.any(),Mockito.any(), Mockito.any(),Mockito.any());
        Mockito.verify(interceptor, Mockito.times(1))
                .afterCompletion(Mockito.any(),Mockito.any(), Mockito.any(),Mockito.any());

    }

    @Test
    public void testGetRequestExpectInvocationAllInterceptorMethodsReturningCodeIsBadRequest() throws Exception {
        Mockito.when(mockMvc.perform(get("/test")))
                .thenThrow(RuntimeException.class);

        mockMvc.perform(get("/testException"))
                .andExpect(status().isBadRequest());

        Mockito.verify(interceptor, Mockito.times(2))
                .preHandle(Mockito.any(),Mockito.any(), Mockito.any());
        Mockito.verify(interceptor, Mockito.times(1))
                .postHandle(Mockito.any(),Mockito.any(), Mockito.any(),Mockito.any());
        Mockito.verify(interceptor, Mockito.times(1))
                .afterCompletion(Mockito.any(),Mockito.any(), Mockito.any(),Mockito.any());


    }


    @RestController
    private static class TestController {
        @GetMapping("/test")
        public ResponseEntity<String> testEndpoint() {
            return ResponseEntity.ok("Test Passed");
        }

        @GetMapping("/testException")
        public String testEndpoint2() {
            throw new RuntimeException();
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Exception> testHandler(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
