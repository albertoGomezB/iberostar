package com.agb.w2w_iberostar.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Handle entity not found exceptions
         * 
         * @param ex
         * @param request
         * @return
         */
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex,
                        HttpServletRequest request) {

                ApiError apiError = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Entity not found")
                                .path(request.getRequestURI())
                                .message(ex.getMessage())
                                .detail("The requested entity with the provided identifier was not found.")
                                .build();

                return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        }

        /**
         * Handle runtime exceptions
         * 
         * @param ex
         * @param request
         * @return
         */
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
                ApiError apiError = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Error in the execution")
                                .path(request.getRequestURI())
                                .message(ex.getMessage())
                                .detail("An unexpected error occurred during execution.")
                                .build();
                return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        }

        /**
         * Handle validation exceptions
         * 
         * @param ex
         * @param request
         * @return
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.joining(", "));

                ApiError apiError = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Errores de validación")
                                .path(request.getRequestURI())
                                .message(errorMessage)
                                .detail("The request contains invalid fields.")
                                .build();

                return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

}
