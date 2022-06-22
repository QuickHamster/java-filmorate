package ru.yandex.practicum.filmoreate.storage;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryLikesStorage {
    private final Map<Long, Set<Long>> likes;

    public InMemoryLikesStorage() {
        likes = new HashMap<>();
    }

    public boolean addFilmToLikesStorage(Long filmId) {
        if (!likes.containsKey(filmId)) {
            Set<Long> set = new HashSet<>();
            likes.put(filmId, set);
            return true;
        } else return false;
    }

    public boolean removeFilmFromLikesStorage(Long filmId) {
        if (likes.containsKey(filmId)) {
            likes.remove(filmId);
            return true;
        } else return false;
    }

    public boolean addLikeToFilm(Long userId, Long filmId) {
        if (likes.containsKey(filmId)) {
            Set<Long> set = likes.get(filmId);
            set.add(userId);
            likes.put(filmId, set);
            return true;
        } else return false;
    }

    public boolean removeLikeFromFilm(Long userId, Long filmId) {
        if (likes.containsKey(filmId)) {
            Set<Long> set = likes.get(filmId);
            set.remove(userId);
            likes.put(filmId, set);
            return true;
        } else return false;
    }

    public Map<Long, Set<Long>> getLikes() {
        return likes;
    }

    public Set<Long> getLikesByFilmId(Long filmId) {
        if (likes.containsKey(filmId)) {
            return likes.get(filmId);
        } else return null;
    }

}
