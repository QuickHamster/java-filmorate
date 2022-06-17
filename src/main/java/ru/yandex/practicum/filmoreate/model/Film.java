package ru.yandex.practicum.filmoreate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private final int MAX_DESCRIPTION_LEN = 200;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
