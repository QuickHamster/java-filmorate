package ru.yandex.practicum.filmoreate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmoreate.controller.FilmController;
import ru.yandex.practicum.filmoreate.exception.*;
import ru.yandex.practicum.filmoreate.model.Film;
import ru.yandex.practicum.filmoreate.service.FilmService;
import ru.yandex.practicum.filmoreate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryLikesStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmoreate.utils.FilmUtil.DATE_BIRTHDAY_CINEMA;
import static ru.yandex.practicum.filmoreate.utils.FilmUtil.MAX_DESCRIPTION_LEN;

@SpringBootTest
class FilmTests {
    private InMemoryFilmStorage inMemoryFilmStorage;
    private FilmService filmService;
    private InMemoryLikesStorage inMemoryLikesStorage;
    private FilmController filmController;
    private InMemoryUserStorage inMemoryUserStorage;
    private Film film;

    @BeforeEach
    public void beforeEach() {
        film = new Film(0L, "name", "description", LocalDate.now().minusYears(1), 110, "", "");
        this.inMemoryFilmStorage = new InMemoryFilmStorage();
        this.inMemoryLikesStorage = new InMemoryLikesStorage();
        this.inMemoryUserStorage = new InMemoryUserStorage();
        this.filmService = new FilmService(this.inMemoryFilmStorage, this.inMemoryLikesStorage, this.inMemoryUserStorage);
        this.filmController = new FilmController(inMemoryFilmStorage, filmService, inMemoryLikesStorage);
    }

    @Test
    void nameIsNull() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Film(0L, null, "description", LocalDate.now().minusYears(1), 110, "", ""));
        assertEquals("name is marked non-null but is null", exception.getMessage());
    }

    @Test
    void releaseDateIsNull() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Film(0L, "name", "description", null, 110, "", ""));
        assertEquals("releaseDate is marked non-null but is null", exception.getMessage());
    }

    @Test
    void durationIsNull() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Film(0L, "name", "description", LocalDate.now().minusYears(1), null, "", ""));
        assertEquals("duration is marked non-null but is null", exception.getMessage());
    }

    @Test
    void nameIsBlank() {
        film.setName("");
        Exception exception = assertThrows(InvalidFilmName.class, () -> filmController.create(film));
        assertEquals("???????????????? ???????????? ???? ???????????? ???????? ????????????.", exception.getMessage());
    }

    @Test
    void descriptionBigSize() {
        film.setDescription("1234567890".repeat(21));
        filmController.create(film);
        assertEquals(MAX_DESCRIPTION_LEN, film.getDescription().length());
    }

    @Test
    void releaseDateEarlierThan28121895() {
        film.setReleaseDate(DATE_BIRTHDAY_CINEMA.minusDays(1));
        Exception exception = assertThrows(InvalidFilmRelease.class, () -> filmController.create(film));
        assertEquals("???????? ???????????? ???????????? ???? ?????????? ???????? ???????????? 28 ?????????????? 1895 ???????? (???????? ???????????????? ????????).", exception.getMessage());
    }

    @Test
    void durationIsPositive() {
        film.setDuration(-1);
        Exception exception = assertThrows(InvalidFilmDuration.class, () -> filmController.create(film));
        assertEquals("?????????????????????????????????? ???????????? ???????????? ???????? ??????????????????????????.", exception.getMessage());
    }

    @Test
    void bodyIsNull() {
        film = null;
        Exception exception = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("???????? ?????????????? ???? ???????????? ???????? ????????????.", exception.getMessage());
    }
}
