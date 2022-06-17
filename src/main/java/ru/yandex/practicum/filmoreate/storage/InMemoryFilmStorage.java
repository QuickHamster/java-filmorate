package ru.yandex.practicum.filmoreate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmoreate.model.Film;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    @Override
    public long add(Film film) {
        return 0;
    }

    @Override
    public long delete(Film film) {
        return 0;
    }

    @Override
    public long update(Film film) {
        return 0;
    }
}
