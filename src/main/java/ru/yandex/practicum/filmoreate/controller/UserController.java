package ru.yandex.practicum.filmoreate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmoreate.model.User;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(
        value = "/users",           
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;

    public UserController(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.debug("Текущее количество пользователей: {}", inMemoryUserStorage.getUsers().size());
        return inMemoryUserStorage.getUsers();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@Valid @RequestBody User user) {
        long userId = inMemoryUserStorage.add(user);
        log.debug("Добавлен пользователь: {}", inMemoryUserStorage.findUserById(userId));
        return inMemoryUserStorage.findUserById(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User put(@Valid @RequestBody User user) {
        long userId = inMemoryUserStorage.update(user);
        log.debug("Обновлен пользователь: {}", inMemoryUserStorage.findUserById(userId));
        return inMemoryUserStorage.findUserById(userId);
    }


}
