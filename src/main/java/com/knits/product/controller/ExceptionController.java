package com.knits.product.controller;

import com.knits.product.exceptions.AppException;
import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.SystemException;
import com.knits.product.exceptions.UserException;
import com.knits.product.service.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleUserException(UserException ex) {
        return wrapIntoResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleSystemException(SystemException ex) {
        return wrapIntoResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(),ex);
        ExceptionDto exDto = new ExceptionDto(ExceptionCodes.UNMAPPED_EXCEPTION_CODE,ex.getMessage());
        return wrapIntoResponseEntity(exDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity (AppException ex, HttpStatus status){
        log.error(ex.getMessage(),ex);
        return ResponseEntity
                .status(status)
                .body(new ExceptionDto(ex));
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity (ExceptionDto exDto, HttpStatus status){
        return ResponseEntity
                .status(status)
                .body(exDto);
    }
}
