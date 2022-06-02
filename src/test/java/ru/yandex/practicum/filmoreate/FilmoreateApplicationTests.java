package ru.yandex.practicum.filmoreate;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmoreate.controller.UserController;
import ru.yandex.practicum.filmoreate.exception.InvalidEmailException;
import ru.yandex.practicum.filmoreate.exception.InvalidUserLogin;
import ru.yandex.practicum.filmoreate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmoreateApplicationTests {

	static UserController userController = new UserController();

	@Test
	void emailIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User(null, "login", "name", LocalDate.now().minusYears(1)));
		assertEquals("email is marked non-null but is null", exception.getMessage());
	}

	@Test
	void loginIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User("email@domain.ru", null, "name", LocalDate.now().minusYears(1)));
		assertEquals("login is marked non-null but is null", exception.getMessage());
	}

	@Test
	void nameIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User("email@domain.ru", "login", null, LocalDate.now().minusYears(1)));
		assertEquals("name is marked non-null but is null", exception.getMessage());
	}

	@Test
	void birthdayIsNull() {
		Exception exception = assertThrows(RuntimeException.class, () -> new User("email@domain.ru", "login", "name", null));
		assertEquals("birthday is marked non-null but is null", exception.getMessage());
	}

	@Test
	void emailIsBlank() {
		User user1 = new User("email@domain.ru", "login", "name", LocalDate.now().minusYears(1));
		user1.setEmail("");
		Exception exception = assertThrows(InvalidEmailException.class, () -> userController.create(user1));
		assertEquals("Адрес электронной почты не должен быть пустым.", exception.getMessage());
	}

	@Test
	void emailNotContainSymbolAmpersand() {
		User user1 = new User("email@domain.ru", "login", "name", LocalDate.now().minusYears(1));
		user1.setEmail("email.domain.ru");
		Exception exception = assertThrows(InvalidEmailException.class, () -> userController.create(user1));
		assertEquals("Неверный формат адреса электронной почты (отсутствует символ '@').", exception.getMessage());
	}

	@Test
	void loginIsBlank() {
		User user1 = new User("email@domain.ru", "login", "name", LocalDate.now().minusYears(1));
		user1.setLogin("");
		Exception exception = assertThrows(InvalidUserLogin.class, () -> userController.create(user1));
		assertEquals("Логин пользователя пустой или содержит символ 'пробел'.", exception.getMessage());
	}

	/*@Test
	void emailIsNull() {
		final RuntimeException  exception = assertThrows(
				RuntimeException.class,
				new Executable() {
					@Override
					public void execute() {
						User user1 = new User(null, null, null, null);
					}
				});
		assertEquals("email is marked non-null but is null", exception.getMessage());
	}

	@Test
	public void  loginIsNull() {
		final RuntimeException  exception = assertThrows(
				RuntimeException.class,
				new Executable() {
					@Override
					public void execute() {
						User user1 = new User("email@domain.ru", null, null, null);
					}
				});
		assertEquals("login is marked non-null but is null", exception.getMessage());
	}

	@Test
	public void  nameIsNull() {
		final RuntimeException  exception = assertThrows(
				RuntimeException.class,
				new Executable() {
					@Override
					public void execute() {
						User user1 = new User("email@domain.ru", "login", null, null);
					}
				});
		assertEquals("name is marked non-null but is null", exception.getMessage());
	}

	@Test
	public void  birthdayIsNull() {
		final RuntimeException  exception = assertThrows(
				RuntimeException.class,
				new Executable() {
					@Override
					public void execute() {
						User user1 = new User("email@domain.ru", "login", "name", null);
					}
				});
		assertEquals("birthday is marked non-null but is null", exception.getMessage());
	}

	@Test
	public void  emailIsEmpty() {
		User user1 = new User("", "login", "name", LocalDate.now().minusYears(1));
		final MethodArgumentNotValidException exception = assertThrows(
				MethodArgumentNotValidException.class,
				new Executable() {
					@Override
					public void execute() {
						User user1 = new User("", "login", "name", LocalDate.now().minusYears(1));
					}
				});
		assertEquals("Email is required.", exception.getMessage());
		//assertEquals("Адрес электронной почты не должен быть пустым.", exception.getMessage());
	}*/
}
