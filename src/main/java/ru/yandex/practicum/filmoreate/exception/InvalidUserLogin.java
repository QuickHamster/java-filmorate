package ru.yandex.practicum.filmoreate.exception;

public class InvalidUserLogin extends RuntimeException{

    public InvalidUserLogin() {
    }

    public InvalidUserLogin(String message) {
        super(message);
    }

    public InvalidUserLogin(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserLogin(Throwable cause) {
        super(cause);
    }

    public InvalidUserLogin(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
