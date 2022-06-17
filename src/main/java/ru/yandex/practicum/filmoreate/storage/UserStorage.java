package ru.yandex.practicum.filmoreate.storage;

import ru.yandex.practicum.filmoreate.model.User;

public interface UserStorage {
    long add(User user);
    long delete(long userId);
    long update(User user);
}
