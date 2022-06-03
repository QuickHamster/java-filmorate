package ru.yandex.practicum.filmoreate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmoreate.exception.*;
import ru.yandex.practicum.filmoreate.model.User;
import ru.yandex.practicum.filmoreate.utils.IdGenerator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(
        //value = "/api/v1/users",  // T0D0 for production
        value = "/users",           // for Postman tests
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping// ("/list") // T0D0 for production
    public Collection<User> getUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@Valid @RequestBody User user)  {
        userOperation(true, user);
        log.debug("Добавлен пользователь: " + user);
        return user;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User put(@Valid @RequestBody User user) {
        userOperation(false, user);
        log.debug("Обновлен пользователь: " + user);
        return user;
    }

    private void userOperation (boolean newUser, User user)  {

        if (user != null) {

            if (user.getEmail().isBlank()) {
                log.debug("Пустое поле адреса электронной почты.");
                throw new InvalidEmailException("Адрес электронной почты не должен быть пустым.");
            }

            if (!user.getEmail().contains("@")) {
                log.debug("В поле адреса электронной почты отсутствует символ '@'.");
                throw new InvalidEmailException("Неверный формат адреса электронной почты (отсутствует символ '@').");
            }

            if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
                log.debug("Некорректное значение поле логина.");
                throw new InvalidUserLogin("Логин пользователя пустой или содержит символ 'пробел'.");
            }

            if (user.getName().isBlank()) {
                log.info("Пустое имя пользователя - подстановка логина.");
                user.setName(user.getLogin());
            }

            if (user.getBirthday().isAfter(LocalDate.now())) {
                log.debug("Некорректное значение даты рождения.");
                throw new InvalidUserBirthday("Пользователи нарушившие пространственно-временной континуум не допускаются к регистрации (дата рождения не может быть в будущем).");
            }

            if (newUser) {
                user.setId(IdGenerator.createUserId());
                users.put(user.getId(), user);
            } else if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            } else throw new ValidationException("Пользователь с id = " + user.getId() + " не существует.");

        } else {
            log.debug("Пустое тело запроса.");
            throw new ValidationException("Тело запроса не должно быть пустым.");
        }
    }
}
