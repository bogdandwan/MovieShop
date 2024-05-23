package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.enums.ExpositionSort;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ExpositionSearch {

    private String addressLike;
    private String cityLike;
    private List<MovieItem> movieItemsLike;
    private String phoneNumber;
    private ExpositionSort expositionSort;
    private String bankAccNumberLike;

}
