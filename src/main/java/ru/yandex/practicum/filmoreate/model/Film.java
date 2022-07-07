package ru.yandex.practicum.filmoreate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.yandex.practicum.filmoreate.utils.FilmUtil.MAX_DESCRIPTION_LEN;

@Data
@AllArgsConstructor
public class Film {

    private Long id;

    @NonNull
    @NotBlank(message = "Name is required.")
    private String name;

    @Size(max = MAX_DESCRIPTION_LEN, message = "Description too long!")
    private String description;

    @NonNull
    @Past(message = "Invalid past releaseDate.")
    private LocalDate releaseDate;

    @NonNull
    @Positive(message = "Duration film should be positive.")
    private Integer duration;

    private String genre;
    
    private String rate;
}
