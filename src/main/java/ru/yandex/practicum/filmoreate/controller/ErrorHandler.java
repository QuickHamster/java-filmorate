package ru.yandex.practicum.filmoreate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmoreate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmoreate.exception.ValidationException;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Integer> handleValidationException(final ValidationException e) {
        return Map.of(e.getMessage(), e.hashCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Integer> handleFilmNotFoundException(final FilmNotFoundException e) {
        return Map.of(e.getMessage(), e.hashCode());
    }

}
