package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.enums.Genre;
import springbootapp.movieclub.entity.enums.MovieSort;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MovieSearch {

    private String nameLike;
    private Integer year;
    private Genre genre;
    private Integer length;
    private List<Genre> genres;
    private Integer lengthTo;
    private Integer lengthFrom;
    private Long actorId;
    private String fullText;
    private MovieSort movieSort;

}
