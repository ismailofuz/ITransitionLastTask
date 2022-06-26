package com.example.itransitionlasttask.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
