package com.khalid.estore.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerServiceException extends RuntimeException {

    private String developerMessage;
    private String userMessage;
    private HttpStatus httpStatus;

}