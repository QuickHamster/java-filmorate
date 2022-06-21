package ru.yandex.practicum.filmoreate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmoreate.model.Film;

import java.util.*;

@Component
public class InMemoryLikesStorage {
    private final Map<Long, Set<Long>> likes;

    public InMemoryLikesStorage() {
        likes = new HashMap<Long, Set<Long>>();
    }

    public boolean addLikeToFilm(Long userId, Long filmId) {
        if (likes.containsKey(filmId)) {
            Set<Long> set = likes.get(filmId);
            set.add(userId);
            likes.put(filmId, set);
            return false;
        } else {
            Set<Long> set = new HashSet<>();
            set.add(userId);
            likes.put(filmId, set);
            return true;
        }
    }

    public boolean removeLikeFromFilm(Long userId, Long filmId) {
        if (likes.containsKey(filmId)) {
            Set<Long> set = likes.get(filmId);
            set.remove(userId);
            likes.put(filmId, set);
            return false;
        } else return true;
    }

    public Map<Long, Set<Long>> getLikes() {
        return likes;
    }

    /*public Set<Long> getLikesByFilmId(Long filmId) {
        if (likes.containsKey(filmId)) {
            return likes.get(filmId);
        } else return null;
    }*/

}
