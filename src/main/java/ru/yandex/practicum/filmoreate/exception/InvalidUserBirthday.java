package ru.yandex.practicum.filmoreate.exception;

public class InvalidUserBirthday extends RuntimeException{

    public InvalidUserBirthday() {
    }

    public InvalidUserBirthday(String message) {
        super(message);
    }

    public InvalidUserBirthday(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserBirthday(Throwable cause) {
        super(cause);
    }

    public InvalidUserBirthday(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
