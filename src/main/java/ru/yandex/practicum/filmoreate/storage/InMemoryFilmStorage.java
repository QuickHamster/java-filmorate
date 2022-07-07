package ru.yandex.practicum.filmoreate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmoreate.exception.*;
import ru.yandex.practicum.filmoreate.model.Film;
import ru.yandex.practicum.filmoreate.utils.IdGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmoreate.utils.FilmUtil.DATE_BIRTHDAY_CINEMA;
import static ru.yandex.practicum.filmoreate.utils.FilmUtil.MAX_DESCRIPTION_LEN;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public long add(Film film) {
        return makeFilm(true, film);
    }

    @Override
    public long delete(long filmId) {
        if (films.containsKey(filmId)) {
            films.remove(filmId);
            return filmId;
        } else throw new FilmNotFoundException(String.format("Фильм # %d не найден.", filmId));
    }

    @Override
    public long update(Film film) {
        return makeFilm(false, film);
    }

    private long makeFilm(boolean newFilm, Film film) {

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
                return film.getId();
            } else if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
                return film.getId();
            } else {
                log.debug("Фильм с id = {} не существует.", film.getId());
                throw new ValidationException("Фильм с id = " + film.getId() + " не существует.");
            }

        } else {
            log.debug("Пустое тело запроса.");
            throw new ValidationException("Тело запроса не должно быть пустым.");
        }
    }

    @Override
    public List<Film> getFilms() {
        return Collections.list(Collections.enumeration(films.values()));
    }

    @Override
    public Film findFilmById(Long filmId) {
        return films.values().stream()
                .filter(p -> p.getId().equals(filmId))
                .findFirst()
                .orElseThrow(() -> new FilmNotFoundException(String.format("Фильм # %d не найден.", filmId)));
    }
}
