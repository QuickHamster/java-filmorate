package ru.yandex.practicum.filmoreate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmoreate.model.User;
import ru.yandex.practicum.filmoreate.storage.InMemoryFriendsStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;
    private final InMemoryFriendsStorage inMemoryFriendsStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage, InMemoryFriendsStorage inMemoryFriendsStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.inMemoryFriendsStorage = inMemoryFriendsStorage;
    }

    public long addFriend(Long id, Long friendId) {
        if (inMemoryUserStorage.findUserById(id) != null && inMemoryUserStorage.findUserById(friendId) != null) {
            inMemoryFriendsStorage.addFriendsToUser(friendId, id);
            inMemoryFriendsStorage.addFriendsToUser(id, friendId);
        }
        return id;
    }

    public long removeFriend(Long id, Long friendId) {
        if (inMemoryUserStorage.findUserById(id) != null && inMemoryUserStorage.findUserById(friendId) != null) {
            inMemoryFriendsStorage.removeFriendsFromUser(friendId, id);
            inMemoryFriendsStorage.removeFriendsFromUser(id, friendId);
        }
        return id;
    }

    public List<User> getFriends(Long id) {
        Set<Long> setFriends = inMemoryFriendsStorage.getFriendsByUserId(id);
        List<User> userList = new ArrayList<>();
        for (Long fiendsId : setFriends) {
            userList.add(inMemoryUserStorage.findUserById(fiendsId));
        }
        return userList;
    }

    public List<User> getMutualFriends(Long id, Long otherId) {
        List mutualFriendsList = new ArrayList<User>();
        Set<Long> setFriendsId = inMemoryFriendsStorage.getFriendsByUserId(otherId);
        Set<Long> setFriendsOtherId = inMemoryFriendsStorage.getFriendsByUserId(id);
        if (setFriendsId != null && setFriendsOtherId != null) {
            List<Long> mutualFriends = setFriendsId.stream()
                    .filter(setFriendsOtherId::contains)
                    .collect(Collectors.toList());

            for (Long userId : mutualFriends) {
                mutualFriendsList.add(inMemoryUserStorage.findUserById(userId));
            }
        }
        return mutualFriendsList;
    }
}
