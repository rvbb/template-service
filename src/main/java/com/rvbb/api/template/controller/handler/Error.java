package com.rvbb.api.template.controller.handler;

import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class Error {
  private LocalDateTime timestamp;
  private int code;
  private String message;
  private List<NestedError> nestedErrors;

  public Error() {
    this.timestamp = LocalDateTime.now();
  }

  public Error(String message) {
    this();
    this.message = message;
  }

  public Error(int code) {
    this();
    this.code = code;
  }

  public Error(String message, int code) {
    this(message);
    this.code = code;
  }

  private void addSubError(NestedError subError) {
    if (nestedErrors == null) {
      nestedErrors = new ArrayList<>();
    }
    nestedErrors.add(subError);
  }

  private void addValidationError(String object, String field, Object rejectedValue, String message) {
    addSubError(new InputValidationError<>(object, field, rejectedValue, message));
  }

  private void addValidationError(String object, String message) {
    addSubError(new InputValidationError<>(object, message));
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
