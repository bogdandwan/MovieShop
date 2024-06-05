package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.RentalSort;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RentalSearch {

    private Exposition exposition;
    private LocalDate rentalDate;
    private LocalDate rentalExpiration;
    private LocalDate returnDate;
    private User client;
    private User worker;
    private Boolean active;
    private RentalSort rentalSort;
    private Integer countMovieItemId;
}
