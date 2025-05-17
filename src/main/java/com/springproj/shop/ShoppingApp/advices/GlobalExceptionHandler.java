package com.springproj.shop.ShoppingApp.advices;

import com.springproj.shop.ShoppingApp.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>>handleResourceNotFound(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError=ApiError.builder().message(resourceNotFoundException.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND).build();
        return buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> validations=methodArgumentNotValidException.getBindingResult()
                .getAllErrors().stream()
                .map(e->e.getDefaultMessage()).toList();
        ApiError apiError=ApiError.builder().subErrors(validations).status(HttpStatus.BAD_REQUEST)
                .message("Please correct the Incorrect Entries!").build();
        return buildErrorResponseEntity(apiError);

    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(DataIntegrityViolationException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(NestedExceptionUtils.getMostSpecificCause(exception).getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .subErrors(errorMessages)
                .message("Please correct the Incorrect Entries!")
                .build();
        return buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }
    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
