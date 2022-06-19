package ru.yandex.practicum.filmoreate.storage;

import ru.yandex.practicum.filmoreate.model.User;

import java.util.List;

public interface UserStorage {
    long add(User user);
    long delete(long userId);
    long update(User user);
    List<User> getUsers();
    User findUserById(Long userId);
    List<User> getFriendsList();
}
