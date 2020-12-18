package com.rvbb.api.template.controller.handler;

import com.rvbb.api.template.dto.Response;
import com.rvbb.api.template.exception.BizLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@ControllerAdvice
@Slf4j
public class BizLogicExceptionHandler extends ResponseEntityExceptionHandler {


  /**
   * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
   *
   * @param ex      MissingServletRequestParameterException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
    String message = ex.getParameterName() + " parameter is missing";
    return buildResponseEntity(BAD_REQUEST, new Error(message, BAD_REQUEST.value()));
  }

  /**
   * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
   *
   * @param ex      HttpMediaTypeNotSupportedException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE,
            new Error(builder.substring(0, builder.length() - 2), UNSUPPORTED_MEDIA_TYPE.value()));
  }

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {

    Error apiError = new Error("Validation error", BAD_REQUEST.value());
    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  /**
   * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
   *
   * @param ex the ConstraintViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(
      javax.validation.ConstraintViolationException ex) {
    Error apiError = new Error("Validation error", BAD_REQUEST.value());
    apiError.addValidationErrors(ex.getConstraintViolations());
    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  /**
   * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
   *
   * @param ex the EntityNotFoundException
   * @return the ApiError object
   */
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
    Error error = new Error(ex.getMessage(), NOT_FOUND.value());
    return buildResponseEntity(NOT_FOUND, error);
  }

  /**
   * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
   *
   * @param ex      HttpMessageNotReadableException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      @NonNull HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
    String message = "Malformed JSON request";
    return buildResponseEntity(BAD_REQUEST, new Error(message, BAD_REQUEST.value()));
  }

  /**
   * Handle HttpMessageNotWritableException.
   *
   * @param ex      HttpMessageNotWritableException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      @NonNull HttpMessageNotWritableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    String message = "Error writing JSON output";
    return buildResponseEntity(INTERNAL_SERVER_ERROR, new Error(message, INTERNAL_SERVER_ERROR.value()));
  }

  /**
   * Handle NoHandlerFoundException.
   *
   * @param ex      NoHandlerFoundException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      @NonNull NoHandlerFoundException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    String message = String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL());
    Error error = new Error(message, BAD_REQUEST.value());
    return buildResponseEntity(BAD_REQUEST, error);
  }

  /**
   * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
   *
   * @param ex the DataIntegrityViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      return buildResponseEntity(CONFLICT, new Error("Database error", CONFLICT.value()));
    }
    return buildResponseEntity(INTERNAL_SERVER_ERROR, new Error("Internal Server Error", INTERNAL_SERVER_ERROR.value()));
  }

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
            ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
    Error error = new Error(message, BAD_REQUEST.value());
    return buildResponseEntity(BAD_REQUEST, error);
  }

  @ExceptionHandler(BizLogicException.class)
  protected ResponseEntity<Object> handleBusinessService(BizLogicException ex) {
    Error error = new Error(ex.getMessage(), ex.getCode());
    return buildResponseEntity(CONFLICT, error);
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleInternalException(Exception ex) {
    Error error = new Error(ex.getMessage(), INTERNAL_SERVER_ERROR.value());
    return buildResponseEntity(INTERNAL_SERVER_ERROR, error);
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Error error) {
    return new ResponseEntity<>(Response.fail(error), httpStatus);
  }
}
