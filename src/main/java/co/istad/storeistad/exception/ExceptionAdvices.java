package co.istad.storeistad.exception;


import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.exception.httpstatus.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sattya
 * create at 26/1/24 2:08 AM
 */

@ControllerAdvice
@Slf4j
public class ExceptionAdvices {

    // auth

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<StructureRS> insufficientAuthenticationException(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.UNAUTHORIZED,
               ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StructureRS> authenticationExceptionHandler(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.UNAUTHORIZED,
                MessageConstant.AUTH.INCORRECT_USERNAME_OR_PASSWORD
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<StructureRS> invalidBearerTokenException(InvalidBearerTokenException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StructureRS> accessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.FORBIDDEN);
    }




    // validation

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StructureRS> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");

        if(ex.getSupportedHttpMethods() != null)
            ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

        StructureRS structureRS = new StructureRS(
                HttpStatus.METHOD_NOT_ALLOWED,
                builder.toString()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<StructureRS> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage(), ex);

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");

        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        StructureRS structureRS = new StructureRS(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.toString()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StructureRS> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage(), ex);

        String message = ex.getName() + " should be of type ";
        if(ex.getRequiredType() != null)
            message +=  ex.getRequiredType().getName();
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                message
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage(), ex);

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(BindException ex) {
        log.error(ex.getMessage(), ex);

        List<String> errors = new ArrayList<>();
        List<String> addedField = new ArrayList<>();

        for (FieldError error : ex.getFieldErrors()) {
            if (!addedField.contains(error.getField())) {
                addedField.add(error.getField());
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            if (!addedField.contains(error.getCode()))
                errors.add(error.getCode() + ": " + error.getDefaultMessage());
        }

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StructureRS> badRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<StructureRS> sizeLimitExceededException(SizeLimitExceededException ex) {
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StructureRS> exception(Exception ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StructureRS> notFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(GoneException.class)
    public ResponseEntity<StructureRS> goneException(GoneException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.GONE,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.GONE);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<StructureRS> forbiddenException(ForbiddenException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
    @ExceptionHandler(ProxyException.class)
    public ResponseEntity<StructureRS> proxyException(ProxyException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.PROXY_AUTHENTICATION_REQUIRED,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StructureRS> unauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    @ExceptionHandler(TemporaryException.class)
    public ResponseEntity<StructureRS> temporaryException(TemporaryException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.TEMPORARY_REDIRECT,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.TEMPORARY_REDIRECT);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NoResourceFoundException.class)
//    public ResponseEntity<StructureRS> handleNoResourceFound(NoResourceFoundException ex) {
//        final String path = ex.getMessage();
//
//        if (path != null && path.contains("favicon.ico")) {
//            // Optional: log at debug level instead of error
//            log.debug("Favicon not found: {}", path);
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(StructureRS.builder()
//                            .status(HttpStatus.NOT_FOUND.value())
//                            .message("Favicon not found (ignored).")
//                            .data(null)
//                            .build());
//        }
//
//        log.warn("Static resource not found: {}", path);
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(StructureRS.builder()
//                        .status(HttpStatus.BAD_REQUEST.value())
//                        .message("Requested static resource not found.")
//                        .data(path)
//                        .build());
//    }

}
