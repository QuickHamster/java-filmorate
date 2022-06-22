package ru.yandex.practicum.filmoreate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmoreate.model.Film;
import ru.yandex.practicum.filmoreate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryLikesStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmoreate.utils.FilmUtil.MAX_POPULAR_FILMS_LEN;

@Service
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryLikesStorage inMemoryLikesStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage,
                       InMemoryLikesStorage inMemoryLikesStorage,
                       InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryLikesStorage = inMemoryLikesStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public long addLike(Long id, Long userId) {
         if (inMemoryFilmStorage.findFilmById(id) != null && inMemoryUserStorage.findUserById(userId) != null) {
            inMemoryLikesStorage.addLikeToFilm(userId, id);
        }
        return id;
    }

    public long removeLike(Long id, Long userId) {
        if (inMemoryFilmStorage.findFilmById(id) != null && inMemoryUserStorage.findUserById(userId) != null) {
            inMemoryLikesStorage.removeLikeFromFilm(userId, id);
        }
        return id;
    }

    public List<Film> getPopularFilms(Integer count) {
        int countFilms = (count <= 0 ? MAX_POPULAR_FILMS_LEN : count);

        return inMemoryLikesStorage.getLikes().entrySet().stream()
                .sorted(Map.Entry.comparingByValue((s1, s2) -> Integer.compare(s2.size(), s1.size())))
                .map(Map.Entry::getKey) // после сортировки пары ключ-значение оставляем только ключи
                .limit(countFilms)
                .map(inMemoryFilmStorage::findFilmById) // вместо каждого айдишника находим фильм, используя существующий метод нахождения по id
                .collect(Collectors.toList());
    }

}
