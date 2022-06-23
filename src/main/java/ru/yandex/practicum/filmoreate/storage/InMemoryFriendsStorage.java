package ru.yandex.practicum.filmoreate.storage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryFriendsStorage {

    private final Map<Long, Set<Long>> friends;

    public InMemoryFriendsStorage() {
        friends = new HashMap<>();
    }

    public boolean addFriendsToUser(Long friendsId, Long userId) {
        if (friends.containsKey(userId)) {
            Set<Long> set = friends.get(userId);
            set.add(friendsId);
            friends.put(userId, set);
            return false;
        } else {
            Set<Long> set = new HashSet<>();
            set.add(friendsId);
            friends.put(userId, set);
            return true;
        }
    }

    public boolean removeFriendsFromUser(Long friendsId, Long userId) {
        if (friends.containsKey(userId)) {
            Set<Long> set = friends.get(userId);
            set.remove(friendsId);
            friends.put(userId, set);
            return false;
        } else return true;
    }

    public Set<Long> getFriendsByUserId(Long userId) {
        if (friends.containsKey(userId)) {
            return friends.get(userId);
        } else return null;
    }
}

