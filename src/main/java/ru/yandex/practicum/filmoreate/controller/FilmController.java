package ru.yandex.practicum.filmoreate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmoreate.model.Film;

import ru.yandex.practicum.filmoreate.service.FilmService;
import ru.yandex.practicum.filmoreate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "/films",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmService filmService;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

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

    @DeleteMapping
    public Long remove(@Valid Long filmId) {
        return inMemoryFilmStorage.delete(filmId);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return inMemoryFilmStorage.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}") // пользователь ставит лайк фильму
    @ResponseBody
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        long filmId = filmService.addLike(id, userId);
        return inMemoryFilmStorage.findFilmById(filmId);
    }

    @DeleteMapping("/{id}/like/{userId}") // пользователь удаляет лайк
    @ResponseBody
    public Film removeLike(@PathVariable Long id, @PathVariable Long userId) {
        long filmId = filmService.removeLike(id, userId);
        return inMemoryFilmStorage.findFilmById(filmId);
    }

    @GetMapping("/popular") // popular?count={count} - возвращает список из первых count фильмов по количеству лайков
    @ResponseBody
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "0", required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }

    /*@GetMapping("/popular")
    @ResponseBody
    public List<Film> getPopularFilm() {
        return filmService.getPopularFilms(0);
    }*/
}
