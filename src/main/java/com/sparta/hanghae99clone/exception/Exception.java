package com.sparta.hanghae99clone.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Exception {
    private String errorMessage;
    private HttpStatus httpStatus;
}
