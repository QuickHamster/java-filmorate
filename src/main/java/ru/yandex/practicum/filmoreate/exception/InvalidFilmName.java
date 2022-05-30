package ru.yandex.practicum.filmoreate.exception;

public class InvalidFilmName extends RuntimeException{

    public InvalidFilmName() {
    }

    public InvalidFilmName(String message) {
        super(message);
    }

    public InvalidFilmName(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFilmName(Throwable cause) {
        super(cause);
    }

    public InvalidFilmName(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
