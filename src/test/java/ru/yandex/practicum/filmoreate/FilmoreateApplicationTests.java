package ru.yandex.practicum.filmoreate;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmoreate.exception.InvalidEmailException;
import ru.yandex.practicum.filmoreate.model.User;

import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmoreateApplicationTests {

	@Test
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
		/*final InvalidEmailException exception = assertThrows(
				InvalidEmailException.class,
				new Executable() {
					@Override
					public void execute() {
						User user1 = new User("", "login", "name", LocalDate.now().minusYears(1));
					}
				});
		assertEquals("Адрес электронной почты не должен быть пустым.", exception.getMessage());*/
	}
}
