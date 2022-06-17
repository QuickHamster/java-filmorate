package ru.yandex.practicum.filmoreate.storage;


import ru.yandex.practicum.filmoreate.model.User;

public interface UserStorage {
    long add(User user);
    long delete(User user);
    long update(User user);
}
