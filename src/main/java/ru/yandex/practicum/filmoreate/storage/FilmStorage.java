package ru.yandex.practicum.filmoreate.storage;

import ru.yandex.practicum.filmoreate.model.Film;

public interface FilmStorage {
    long add(Film film);
    long delete(long filmId);
    long update(Film film);
}
