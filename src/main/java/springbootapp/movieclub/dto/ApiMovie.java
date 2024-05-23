package springbootapp.movieclub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.enums.Genre;
import springbootapp.movieclub.entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ApiMovie {

    private Long id;
    private String name;
    private Integer year;
    private Genre genre;
    private Integer length;
    private List<ApiActor> actors;

    public ApiMovie(Movie movie){

        id = movie.getId();
        name = movie.getName();
        year = movie.getYear();
        genre = movie.getGenre();
        length = movie.getLength();
        if (movie.getActors() != null) {
            actors = movie.getActors().stream().map(ApiActor::new).collect(Collectors.toList());
        }

    }

}
