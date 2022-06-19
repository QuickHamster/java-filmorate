package ru.yandex.practicum.filmoreate.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryFriendsStorage {

    private final Map<Long, Set<Long>> friends;

    public InMemoryFriendsStorage() {
        friends = new HashMap<Long, Set<Long>>();
    }

    /*public boolean addUser(Long userId) {
        if (!friends.containsKey(userId)) {
            Set<Long> set = new HashSet<>();
            friends.put(userId, set);
            return true;
        } else return false;
    }*/

    public boolean addFriendsToUser(Long friendsId, Long userId) {
        /*if (friends.containsKey(userId)) {
            Set<Long> set = friends.get(userId);
            set.add(friendsId);
            friends.put(userId, set);
            return true;
        } else return true;*/

        if (friends.containsKey(userId)) {
            Set<Long> set = friends.get(userId);
            set.add(friendsId);
            friends.put(userId, set);
            return false;
        }else {
            Set<Long> set = new HashSet<>();
            set.add(friendsId);
            friends.put(userId, set);
            return true;
        }
    }
}

