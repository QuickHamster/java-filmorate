package ru.yandex.practicum.filmoreate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmoreate.controller.UserController;
import ru.yandex.practicum.filmoreate.exception.InvalidEmailException;
import ru.yandex.practicum.filmoreate.exception.InvalidUserBirthday;
import ru.yandex.practicum.filmoreate.exception.InvalidUserLogin;
import ru.yandex.practicum.filmoreate.exception.ValidationException;
import ru.yandex.practicum.filmoreate.model.Film;
import ru.yandex.practicum.filmoreate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserTests {

	private static final UserController userController = new UserController();
	private User user;

	@BeforeEach
	public void beforeEach() {
		user = new User(0L,"email@domain.ru", "login", "name", LocalDate.now().minusYears(1));
	}

	@Test
	void emailIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User(0L,null, "login", "name", LocalDate.now().minusYears(1)));
		assertEquals("email is marked non-null but is null", exception.getMessage());
	}

	@Test
	void loginIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User(0L,"email@domain.ru", null, "name", LocalDate.now().minusYears(1)));
		assertEquals("login is marked non-null but is null", exception.getMessage());
	}

	@Test
	void nameIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User(0L,"email@domain.ru", "login", null, LocalDate.now().minusYears(1)));
		assertEquals("name is marked non-null but is null", exception.getMessage());
	}

	@Test
	void birthdayIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User(0L,"email@domain.ru", "login", "name", null));
		assertEquals("birthday is marked non-null but is null", exception.getMessage());
	}

	@Test
	void emailIsBlank() {
		user.setEmail("");
		Exception exception = assertThrows(InvalidEmailException.class, () -> userController.create(user));
		assertEquals("Адрес электронной почты не должен быть пустым.", exception.getMessage());
	}

	@Test
	void emailNotContainSymbolAmpersand() {
		user.setEmail("email.domain.ru");
		Exception exception = assertThrows(InvalidEmailException.class, () -> userController.create(user));
		assertEquals("Неверный формат адреса электронной почты (отсутствует символ '@').", exception.getMessage());
	}

	@Test
	void loginIsBlank() {
		user.setLogin("");
		Exception exception = assertThrows(InvalidUserLogin.class, () -> userController.create(user));
		assertEquals("Логин пользователя пустой или содержит символ 'пробел'.", exception.getMessage());
	}

	@Test
	void loginContainSymbolSpace() {
		user.setLogin("lo gin");
		Exception exception = assertThrows(InvalidUserLogin.class, () -> userController.create(user));
		assertEquals("Логин пользователя пустой или содержит символ 'пробел'.", exception.getMessage());
	}

	@Test
	void nameIsBlank() {
		user.setName("");
		userController.create(user);
		assertEquals(user.getLogin(), user.getName());
	}

	@Test
	void birthdayInFuture() {
		user.setBirthday(LocalDate.now().plusDays(1));
		Exception exception = assertThrows(InvalidUserBirthday.class, () -> userController.create(user));
		assertEquals("Пользователи нарушившие пространственно-временной континуум не допускаются к регистрации (дата рождения не может быть в будущем).", exception.getMessage());
	}

	@Test
	void bodyIsNull() {
		user = null;
		Exception exception = assertThrows(ValidationException.class, () -> userController.create(user));
		assertEquals("Тело запроса не должно быть пустым.", exception.getMessage());
	}
}
