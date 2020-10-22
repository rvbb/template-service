package com.smartosc.lending.lms.service.exception;

import com.smartosc.lending.lms.service.dto.IErrorCode;
import com.smartosc.lending.lms.service.dto.Response;
import com.smartosc.lending.lms.service.util.IConst;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final Response<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return Response.fail(IConst.RESPONSE_EXCEPTION + " - " +  ex.getLocalizedMessage(), IErrorCode.EXCEPTION);
    }

    @ExceptionHandler(NotFoundException.class)
    public final Response<Object> handleResourceNotFoundException(NotFoundException ex, WebRequest request) {
        return Response.fail(IConst.MSG_RESOURCE_NOTFOUND + " - " +  ex.getLocalizedMessage(), IErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(InternalServiceException.class)
    public final Response<Object> handleResourceNotFoundException(InternalServiceException ex, WebRequest request) {
        return Response.fail(IConst.RESPONSE_SERVER_PROBLEM + " - " +  ex.getLocalizedMessage(), IErrorCode.SERVER_PROBLEM);
    }
}
