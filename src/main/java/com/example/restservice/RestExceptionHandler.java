package com.example.restservice;

import com.example.restservice.entities.common.CommonResponse;
import com.example.restservice.entities.users.exceptions.UsersException;
import com.example.restservice.apierror.ApiError;
import com.example.restservice.execptions.EntityAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.example.restservice.execptions.EntityNotFoundException;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    HttpMessageNotReadableException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request) {
    String error = "Malformed JSON request";

    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(
      new CommonResponse(
        apiError.getMessage(),
        -1,
        null, // TODO: implement sub errors
        null
      ),
      apiError.getStatus()
    );
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleCommonInternalErrors(
    Exception ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, Messages.ERROR.code, ex.getMessage());

    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
    EntityNotFoundException ex) {
    ApiError apiError = new ApiError(NOT_FOUND, Messages.ERROR.code, ex.getMessage());

    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(EntityAlreadyExistsException.class)
  protected ResponseEntity<Object> handleEntityAlreadyExists(
    EntityAlreadyExistsException ex) {
    ApiError apiError = new ApiError(BAD_REQUEST, Messages.ERROR.ENTITY_ALREADY_EXISTS.code, ex.getMessage());

    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(UsersException.class)
  protected ResponseEntity<Object> handleAccountsException(
    UsersException ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, Messages.ERROR.code, ex.getMessage());

    return buildResponseEntity(apiError);
  }
}
