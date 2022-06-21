package ru.yandex.practicum.filmoreate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmoreate.model.User;
import ru.yandex.practicum.filmoreate.service.UserService;
import ru.yandex.practicum.filmoreate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "/users",           
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserService userService;

    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
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

    @DeleteMapping
    public Long remove(@Valid Long userId) {
        return inMemoryUserStorage.delete(userId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return inMemoryUserStorage.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}") // добавление в друзья
    @ResponseBody
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        long userId = userService.addFriend(id, friendId);
        return inMemoryUserStorage.findUserById(userId);
    }

    @DeleteMapping("/{id}/friends/{friendId}") // удаление из друзей
    @ResponseBody
    public User removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        long userId = userService.removeFriend(id, friendId);
        return inMemoryUserStorage.findUserById(userId);
    }

    @GetMapping("/{id}/friends") // список пользователей, являющихся его друзьями.
    @ResponseBody
    public List<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}") // список друзей, общих с другим пользователем.
    public List<User> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        List<User> mutualFriends = userService.getMutualFriends(id, otherId);
        return mutualFriends;
    }
}
