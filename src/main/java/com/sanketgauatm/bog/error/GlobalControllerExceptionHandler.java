package com.sanketgauatm.bog.error;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class GlobalControllerExceptionHandler  extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ErrorResponse er = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error("Error while processing request: \n path: {} \n {}", request.getRequestURI(), ex.getMessage());
        return buildResponseEntity(er);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse er = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error("Error while processing request: \n path: {} \n {}", request.getRequestURI(), ex.getMessage());
        return buildResponseEntity(er);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
