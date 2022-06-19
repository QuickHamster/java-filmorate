package ru.yandex.practicum.filmoreate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmoreate.model.User;
import ru.yandex.practicum.filmoreate.storage.InMemoryFriendsStorage;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
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
            /*inMemoryUserStorage.findUserById(id).addFriend(friendId);
            inMemoryUserStorage.findUserById(friendId).addFriend(id);*/
        }
        return id;
    }

    public long removeFriend(Long id, Long friendId) {
        inMemoryUserStorage.findUserById(id).getFriends().remove(friendId);
        inMemoryUserStorage.findUserById(friendId).getFriends().remove(id);
        return id;
    }

    public List<User> getFriends(Long id) {
        return inMemoryUserStorage.findUserById(id).getFriends().stream()
                .map(userId -> inMemoryUserStorage.findUserById(userId))
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long id, Long otherId) {
        List mutualFriendsList = new ArrayList<User>();
        if (inMemoryUserStorage.findUserById(id).getFriends() != null && inMemoryUserStorage.findUserById(otherId).getFriends() != null) {
            List<Long> mutualFriends = inMemoryUserStorage.findUserById(id).getFriends().stream()
                    .filter(inMemoryUserStorage.findUserById(otherId).getFriends()::contains)
                    .collect(Collectors.toList());

            for (Long userId : mutualFriends) {
                mutualFriendsList.add(inMemoryUserStorage.findUserById(userId));
            }
        }
        return mutualFriendsList;
    }
}
