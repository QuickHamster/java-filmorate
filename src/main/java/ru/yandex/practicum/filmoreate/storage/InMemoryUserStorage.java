package ru.yandex.practicum.filmoreate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmoreate.exception.*;
import ru.yandex.practicum.filmoreate.model.User;
import ru.yandex.practicum.filmoreate.utils.IdGenerator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public long add(User user) {
        return makeUser(true, user);
    }

    @Override
    public long delete(User user) {
        return makeUser(false, user);
    }

    @Override
    public long update(User user) {
        return 0;
    }

    private long makeUser(boolean newUser, User user) {

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
                return user.getId();
            } else if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                return user.getId();
            } else {
                log.debug("Пользователь с id = {} не существует.", user.getId());
                throw new ValidationException("Пользователь с id = " + user.getId() + " не существует.");
            }

        } else {
            log.debug("Пустое тело запроса.");
            throw new ValidationException("Тело запроса не должно быть пустым.");
        }
    }

    public List<User> getUsers() {
        return Collections.list(Collections.enumeration(users.values()));
    }

    public User findUserById(Long userId) {
        return users.values().stream()
                .filter(p -> p.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new FilmNotFoundException(String.format("Пользователь # %d не найден.", userId)));
    }
}
