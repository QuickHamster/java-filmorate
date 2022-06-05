package ru.yandex.practicum.filmoreate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmoreate.exception.*;
import ru.yandex.practicum.filmoreate.model.Film;

import static ru.yandex.practicum.filmoreate.utils.FilmUtil.DATE_BIRTHDAY_CINEMA;
import static ru.yandex.practicum.filmoreate.utils.FilmUtil.MAX_DESCRIPTION_LEN;
import ru.yandex.practicum.filmoreate.utils.IdGenerator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(
        value = "/films",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Film create(@Valid @RequestBody Film film) {
        filmProcessing(true, film);
        log.debug("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Film put(@Valid @RequestBody Film film) {
        filmProcessing(false, film);
        log.debug("Обновлен фильм: {}", film);
        return film;
    }

    private void filmProcessing(boolean newFilm, Film film) {

        if (film != null) {

            if (film.getName().isBlank()) {
                log.debug("Пустое название фильма");
                throw new InvalidFilmName("Название фильма не должно быть пустым.");
            }

            if (film.getDescription().length() > MAX_DESCRIPTION_LEN) {
                log.info("Превышена допустимая длина описания фильма. Описание обрезано до {} символов.", MAX_DESCRIPTION_LEN);
                film.setDescription(film.getDescription().substring(0, MAX_DESCRIPTION_LEN));
            }

            if (film.getReleaseDate().isBefore(DATE_BIRTHDAY_CINEMA)) {
                log.debug("Некорректная дата релиза.");
                throw new InvalidFilmRelease("Дата релиза фильма не может быть раньше 28 декабря 1895 года (день рождения кино).");
            }

            if (film.getDuration() < 0) {
                log.debug("Отрицательная продолжительность фильма.");
                throw new InvalidFilmDuration("Продолжительность фильма должна быть положительной.");
            }

            if (newFilm) {
                film.setId(IdGenerator.createFilmId());
                films.put(film.getId(), film);
            } else if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
            } else {
                log.debug("Фильм с id = {} не существует.", film.getId());
                throw new ValidationException("Фильм с id = " + film.getId() + " не существует.");
            }

        } else {
            log.debug("Пустое тело запроса.");
            throw new ValidationException("Тело запроса не должно быть пустым.");
        }
    }
}
