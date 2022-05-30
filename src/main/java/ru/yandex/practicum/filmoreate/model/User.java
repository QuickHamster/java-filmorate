package ru.yandex.practicum.filmoreate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
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
    //@NotBlank(message = "Name is required.")
    private String name;

    @NonNull
    @Past(message = "Invalid past date birthday.")
    private LocalDate birthday;

}

