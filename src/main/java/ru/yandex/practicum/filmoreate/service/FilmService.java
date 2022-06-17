package ru.yandex.practicum.filmoreate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmoreate.storage.InMemoryFilmStorage;

@Service
public class FilmService {
    //private final InMemoryFilmStorage inMemoryFilmStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        //this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
}
