package ru.yandex.practicum.filmoreate.exception;

public class InvalidFilmRelease extends RuntimeException{

    public InvalidFilmRelease() {
    }

    public InvalidFilmRelease(String message) {
        super(message);
    }

    public InvalidFilmRelease(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFilmRelease(Throwable cause) {
        super(cause);
    }

    public InvalidFilmRelease(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
