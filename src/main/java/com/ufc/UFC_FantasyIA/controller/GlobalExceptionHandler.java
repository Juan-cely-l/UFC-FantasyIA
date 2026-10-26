package com.ufc.UFC_FantasyIA.controller;

import com.ufc.UFC_FantasyIA.exception.EventNotFoundException;
import com.ufc.UFC_FantasyIA.exception.FighterNotFoundException;
import com.ufc.UFC_FantasyIA.exception.ResourceAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EventNotFoundException.class, FighterNotFoundException.class})
    public ProblemDetail handleNotFound(RuntimeException exception, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ProblemDetail handleConflict(
            ResourceAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        return buildProblemDetail(HttpStatus.CONFLICT, exception.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problem = buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Request validation failed",
                request
        );

        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        problem.setProperty("errors", errors);
        return problem;
    }

    private ProblemDetail buildProblemDetail(
            HttpStatus status,
            String detail,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(status.getReasonPhrase());
        problem.setInstance(URI.create(request.getRequestURI()));
        return problem;
    }
}
