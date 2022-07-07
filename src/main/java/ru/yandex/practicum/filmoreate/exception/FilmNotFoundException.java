package ru.yandex.practicum.filmoreate.exception;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String s) {
        super(s);
    }
}
