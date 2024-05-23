package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.Genre;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.MovieItemSort;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MovieItemSearch {

    private Double price;
    private Movie movie;
    private Exposition exposition;
    private Genre genre;
    private Integer movieLengthTo;
    private Integer movieLengthFrom;
    private String movieNameLike;
    private MovieItemSort movieItemSort;

}
