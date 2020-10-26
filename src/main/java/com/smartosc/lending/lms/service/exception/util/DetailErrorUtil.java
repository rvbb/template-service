package com.smartosc.lending.lms.service.exception.util;

import com.smartosc.lending.lms.service.exception.model.DetailError;
import com.smartosc.lending.lms.service.exception.model.ValidationError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailErrorUtil {

  public static List<DetailError> fromFieldErrors(List<FieldError> fieldErrors) {
    return fieldErrors.stream().map(DetailErrorUtil::fromFieldError).collect(Collectors.toList());
  }

  public static DetailError fromFieldError(FieldError fieldError) {
    return new ValidationError<>(
        fieldError.getObjectName(),
        fieldError.getField(),
        fieldError.getRejectedValue(),
        fieldError.getDefaultMessage()
    );
  }

  public static List<DetailError> fromObjectErrors(List<ObjectError> globalErrors) {
    return globalErrors.stream().map(DetailErrorUtil::fromObjectError).collect(Collectors.toList());
  }

  public static DetailError fromObjectError(ObjectError globalError) {
    return new ValidationError<>(
        globalError.getObjectName(),
        globalError.getDefaultMessage()
    );
  }

  public static List<DetailError> fromConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
    return constraintViolations.stream().map(DetailErrorUtil::fromConstraintViolation).collect(Collectors.toList());
  }

  public static DetailError fromConstraintViolation(ConstraintViolation<?> constraintViolation) {
    return new ValidationError<>(
        constraintViolation.getRootBeanClass().getSimpleName(),
        ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
        constraintViolation.getInvalidValue(),
        constraintViolation.getMessage()
    );
  }
}
