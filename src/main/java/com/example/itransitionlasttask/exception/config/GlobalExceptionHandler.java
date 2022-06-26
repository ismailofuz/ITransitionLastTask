package com.example.itransitionlasttask.exception.config;


import com.example.itransitionlasttask.dto.response.ErrorResponse;
import com.example.itransitionlasttask.exception.BadRequestException;
import com.example.itransitionlasttask.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse userAlreadyExist(BadRequestException exception){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse notFound(NotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

}


