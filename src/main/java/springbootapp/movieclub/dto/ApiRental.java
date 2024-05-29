package springbootapp.movieclub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.Rental;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ApiRental {

    private Long id;
    private ApiUser client;
    private ApiUser worker;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private LocalDate rentalExpiration;
    private List<ApiMovieItem> movieItems;
    private ApiExposition exposition;

    public ApiRental(Rental rental) {
        id = rental.getId();

        if (rental.getClient() != null) {
            client = new ApiUser(rental.getClient());
        }

        if (rental.getWorker() != null){
            worker = new ApiUser(rental.getWorker());
        }
        rentalDate = rental.getRentalDate();
        returnDate = rental.getReturnDate();
        rentalExpiration = rental.getRentalExpiration();

        if (rental.getMovieItems() != null){
            movieItems = rental.getMovieItems().stream().map(ApiMovieItem::new).collect(Collectors.toList());
        }
        if (rental.getExposition() != null){
            exposition = new ApiExposition(rental.getExposition());
        }
    }

}
