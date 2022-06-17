package ru.yandex.practicum.filmoreate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmoreate.model.Film;

import ru.yandex.practicum.filmoreate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(
        value = "/films",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
    /*private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }*/

    @GetMapping
    public Collection<Film> getFilms() {
        log.debug("Текущее количество фильмов: {}", inMemoryFilmStorage.getFilms().size());
        return inMemoryFilmStorage.getFilms();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Film create(@Valid @RequestBody Film film) {
        long filmId = inMemoryFilmStorage.add(film);
        log.debug("Добавлен фильм: {}", inMemoryFilmStorage.findFilmById(filmId));
        return inMemoryFilmStorage.findFilmById(filmId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Film put(@Valid @RequestBody Film film) {
        long filmId = inMemoryFilmStorage.update(film);
        log.debug("Обновлен фильм: {}", inMemoryFilmStorage.findFilmById(filmId));
        return inMemoryFilmStorage.findFilmById(filmId);
    }


}
