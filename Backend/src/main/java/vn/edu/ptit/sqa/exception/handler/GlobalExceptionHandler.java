package vn.edu.ptit.sqa.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.edu.ptit.sqa.exception.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //This exception is thrown when fatal binding errors occur.
//    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<>();
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    //This exception is thrown when argument annotated with @Valid failed validation:
//    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex,
                                                         final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    //This invalid parameter type conversion
//    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
                                                        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName()
                + " should be of type " + ex.getRequiredType();

        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

//    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            final MissingServletRequestPartException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getRequestPartName() + " part is missing";
        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

//    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getParameterName() + " parameter is missing";
        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    //

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
                                                            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }

        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), new Date(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 404

//    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), new Date(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 405

//    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), new Date(), builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 415

//    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        final GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), new Date(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 500
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUserServiceExceptions(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.FORBIDDEN,
                ex.getLocalizedMessage(), new Date(), "error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RuntimeExceptionHandling.class)
    public ResponseEntity<GlobalExceptionError> springHandleNotFound(RuntimeExceptionHandling ex,
                                                                     WebRequest request) {
        log.error("error", ex);
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(),
                new Date(), "not found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<GlobalExceptionError> handleException(UnAuthorizedException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.UNAUTHORIZED, e.getMessage(),
                new Date(), "UNAUTHORIZED");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GlobalExceptionError> handleUnauthorizedException(AuthenticationException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.FORBIDDEN, e.getMessage(),
                new Date(), "Unauthorized");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExistedException.class)
    public ResponseEntity<GlobalExceptionError> handleExistedException(ExistedException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST, e.getMessage(),
                new Date(), "Bad request");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionError> handleException(Exception e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
                new Date(), "INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalExceptionError> handleBadRequestException(BadRequestException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST, e.getMessage(),
                new Date(), "Bad request");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalExceptionError> handleNotFoundException(NotFoundException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST, e.getMessage(),
                new Date(), "Bad request");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<GlobalExceptionError> handleForbiddenException(ForbiddenException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.FORBIDDEN, e.getMessage(),
                new Date(), "Forbidden");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<GlobalExceptionError> handleSqlException(SQLException e) {
        log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(),
                new Date(), "Sql Exception");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
