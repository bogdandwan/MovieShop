package springbootapp.movieclub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.MovieItem;

@Data
@NoArgsConstructor
public class ApiMovieItem {

    private Long id;
    private Double price;
    private ApiMovie movie;
    private ApiExposition exposition;
    private Boolean available; //da li je dostupan ili nije


    public ApiMovieItem (MovieItem movieItem){
        id = movieItem.getId();
        price = movieItem.getPrice();
        if (movieItem.getMovie() != null){
            movie = new ApiMovie(movieItem.getMovie());
        }
        if (movieItem.getExposition() != null){
            exposition = new ApiExposition(movieItem.getExposition());
        }
        available = movieItem.getAvailable();

    }



}
