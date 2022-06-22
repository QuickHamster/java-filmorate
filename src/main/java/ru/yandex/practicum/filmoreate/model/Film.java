package ru.yandex.practicum.filmoreate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static ru.yandex.practicum.filmoreate.utils.FilmUtil.MAX_DESCRIPTION_LEN;

@Data
@AllArgsConstructor
public class Film {

    /*public Film() {
        this.likes = new HashSet<>();
    }*/

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

    @NonNull
    private Integer rate;

    //private Set<Long> likes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public Set<Long> getLikes() {
        return likes;
    }

    public void setLikes(Set<Long> likes) {
        this.likes = likes;
        rate = likes.size();
    }*/
}
