package ru.yandex.practicum.filmoreate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmoreate.model.Film;
import ru.yandex.practicum.filmoreate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryLikesStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import static java.util.Comparator.comparing;

import java.util.*;

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
        /*if (inMemoryFilmStorage.findFilmById(id) != null && inMemoryUserStorage.findUserById(userId) != null) {
           Set<Long> likes = inMemoryFilmStorage.findFilmById(id).getLikes();
           likes.add(userId);
           inMemoryFilmStorage.findFilmById(id).setLikes(likes);
        }*/

         if (inMemoryFilmStorage.findFilmById(id) != null && inMemoryUserStorage.findUserById(userId) != null) {
            inMemoryLikesStorage.addLikeToFilm(userId, id);
        }
        return id;
    }

    public long removeLike(Long id, Long userId) {
        /*if (inMemoryFilmStorage.findFilmById(id) != null && inMemoryUserStorage.findUserById(userId) != null) {
            Set<Long> likes = inMemoryFilmStorage.findFilmById(id).getLikes();
            likes.remove(userId);
            inMemoryFilmStorage.findFilmById(id).setLikes(likes);
        }*/
        if (inMemoryFilmStorage.findFilmById(id) != null && inMemoryUserStorage.findUserById(userId) != null) {
            inMemoryLikesStorage.removeLikeFromFilm(userId, id);
        }
        return id;
    }

    /*public List<Film> getPopularFilms(Integer count) {
        Map<Set<Long>, Long> sortBySize = new TreeMap<>(comparing(Set::size));
        int countFilms = (count <= 0 ? MAX_POPULAR_FILMS_LEN : count);

        return null;
    }*/

    private class SetComparator implements Comparator<Set<Long>> {

        public int compare(Set<Long> t1, Set<Long> t2) {
            if (t1.size() == t2.size()) {
                return 0;
            } else return t1.size() < t2.size() ? 1 : -1;
        }
    }

    public List<Film> getPopularFilms(Integer count) {
        //TreeMap<Set<Long>, Long> sortBySize = new TreeMap<>(comparing(Set::size));
        TreeMap<Set<Long>, Long> sortBySize = new TreeMap<>(new SetComparator());

        for (Map.Entry<Long, Set<Long>> likeStruct : inMemoryLikesStorage.getLikes().entrySet()){
            sortBySize.put(likeStruct.getValue(), likeStruct.getKey());
        }

        int countFilms = (count <= 0 ? MAX_POPULAR_FILMS_LEN : count);

        //TreeMap<Set<Long>, Long> subSortBySize = new TreeMap<>(comparing(Set::size));
        TreeMap<Set<Long>, Long> subSortBySize = new TreeMap<>(new SetComparator());

        int counter = 0;
        for (Map.Entry<Set<Long>, Long> likeStruct : sortBySize.entrySet()) {
            Set<Long> set = new HashSet<>();
            set = likeStruct.getKey();
            subSortBySize.put(set, likeStruct.getValue());
            if (counter < countFilms) {counter++;}
            else break;
        }

        /*TreeMap<Set<Long>, Long> newSortBySize = sortBySize.entrySet().stream()
                .limit(countFilms)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);*/

        List<Film> listPopularFilms = new ArrayList<>();
        for(Map.Entry<Set<Long>, Long> likeStruct : subSortBySize.entrySet()) {
            listPopularFilms.add(inMemoryFilmStorage.findFilmById(likeStruct.getValue()));
        }

        return listPopularFilms;


        /*List sortPopularFilms = new ArrayList(inMemoryLikesStorage.getLikes().entrySet());
        Collections.sort(sortPopularFilms, new Comparator<Map.Entry<Long, Set<Long>>>() {
            @Override
            public int compare(Map.Entry<Long, Set<Long>> a, Map.Entry<Long, Set<Long>> b) {
                return a.getValue().size() - b.getValue().size();
            }
        });
        List<Film> listPopularFilms = new ArrayList<>();
        int countFilms = (count <= 0 ? MAX_POPULAR_FILMS_LEN : count);
        List subPopularFilms = new ArrayList<>();
        subPopularFilms = sortPopularFilms.stream().limit(countFilms).collect(Collectors.toList());
        for (int i = 0; i < (countFilms); i++) {
            listPopularFilms.add()
        }
        return listPopularFilms;*/
    }


    /*public List<Film> getPopularFilms(Long id) {
        Set<Long> setFriends = InMemoryLikesStorage.getFriendsByUserId(id);
        List<User> userList = new ArrayList<>();
        for (Long fiendsId : setFriends) {
            userList.add(inMemoryFilmStorage.findFilmById(fiendsId));
        }
        return userList;
    }*/

}
