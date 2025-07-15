package co.istad.storeistad.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * @author Sombath
 * create at 12/9/23 11:05 PM
 */

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    @Lazy
    private HandlerExceptionResolver resolver;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) {
        resolver.resolveException(request, response, null, authenticationException);
    }
}