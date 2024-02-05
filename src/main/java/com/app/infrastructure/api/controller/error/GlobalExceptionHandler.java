package com.app.infrastructure.api.controller.error;

import com.app.application.dto.response.ResponseDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDto<Object>> defaultErrorHandler(HttpServletRequest req, Exception ex) {

        var status = getStatus(req);
        return new ResponseEntity<>(new ResponseDto<>(ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
