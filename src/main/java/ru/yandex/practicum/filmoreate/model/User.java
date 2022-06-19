package ru.yandex.practicum.filmoreate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private Long id;

    @NonNull
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email. Try again.")
    private String email;

    @NonNull
    @NotBlank(message = "login is required.")
    private String login;

    @NonNull
    private String name;

    @NonNull
    @Past(message = "Invalid past date birthday.")
    private LocalDate birthday;
}

