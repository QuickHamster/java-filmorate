package ru.yandex.practicum.filmoreate.exception;

public class InvalidFilmDuration extends RuntimeException{

    public InvalidFilmDuration() {
    }

    public InvalidFilmDuration(String message) {
        super(message);
    }

    public InvalidFilmDuration(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFilmDuration(Throwable cause) {
        super(cause);
    }

    public InvalidFilmDuration(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
