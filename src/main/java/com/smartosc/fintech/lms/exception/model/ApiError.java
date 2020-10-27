package com.smartosc.fintech.lms.exception.model;

import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ApiError {

  private HttpStatus httpStatus;

  private LocalDateTime localDateTime;

  private String message;

  private String debugMessage;

  private List<ApiSubError> apiSubErrors;

  public ApiError() {
    this.localDateTime = LocalDateTime.now();
  }

  public ApiError(HttpStatus httpStatus) {
    this();
    this.httpStatus = httpStatus;
  }

  public ApiError(HttpStatus httpStatus, Throwable throwable) {
    this();
    this.httpStatus = httpStatus;
    this.message = "Unexpected error";
    this.debugMessage = throwable.getMessage();
  }

  public ApiError(HttpStatus httpStatus, String message, Throwable throwable) {
    this();
    this.httpStatus = httpStatus;
    this.message = message;
    this.debugMessage = throwable.getMessage();
  }

  private void addSubError(ApiSubError subError) {
    if (apiSubErrors == null) {
      apiSubErrors = new ArrayList<>();
    }
    apiSubErrors.add(subError);
  }

  private void addValidationError(String object, String field, Object rejectedValue, String message) {
    addSubError(new ApiValidationError<>(object, field, rejectedValue, message));
  }

  private void addValidationError(String object, String message) {
    addSubError(new ApiValidationError<>(object, message));
  }

  private void addValidationError(FieldError fieldError) {
    this.addValidationError(
        fieldError.getObjectName(),
        fieldError.getField(),
        fieldError.getRejectedValue(),
        fieldError.getDefaultMessage());
  }

  public void addValidationErrors(List<FieldError> fieldErrors) {
    fieldErrors.forEach(this::addValidationError);
  }

  private void addValidationError(ObjectError objectError) {
    this.addValidationError(
        objectError.getObjectName(),
        objectError.getDefaultMessage());
  }

  public void addValidationError(List<ObjectError> globalErrors) {
    globalErrors.forEach(this::addValidationError);
  }

  /**
   * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
   *
   * @param cv the ConstraintViolation
   */
  private void addValidationError(ConstraintViolation<?> cv) {
    this.addValidationError(
        cv.getRootBeanClass().getSimpleName(),
        ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
        cv.getInvalidValue(),
        cv.getMessage());
  }

  public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    constraintViolations.forEach(this::addValidationError);
  }

}
