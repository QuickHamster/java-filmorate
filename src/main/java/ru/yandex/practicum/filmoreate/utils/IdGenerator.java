package ru.yandex.practicum.filmoreate.utils;

public class IdGenerator {
    private static long userId;
    private static long filmId;

    public static long createUserId() {
        return ++userId;
    }

    public static long createFilmId() {
        return ++filmId;
    }
}

