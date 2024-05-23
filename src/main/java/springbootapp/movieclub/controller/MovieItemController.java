package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiMovieItem;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.Genre;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.enums.MovieItemSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.MovieItemSearch;
import springbootapp.movieclub.service.ExpositionService;
import springbootapp.movieclub.service.MovieItemService;
import springbootapp.movieclub.service.MovieService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MovieItemController {

    final MovieItemService movieItemService;
    final ExpositionService expositionService;
    final MovieService movieService;



    @PostMapping("/movie-item")
    public void saveOrUpdateMovieItem(@RequestBody ApiMovieItem apiMovieItem) {

        final MovieItem movieItem;

        if (apiMovieItem.getId() == null) {
            movieItem = new MovieItem();
        }else {
            movieItem = movieItemService.findById(apiMovieItem.getId());
            if (movieItem == null) {
                throw new NotFoundException("Movie item not found");
            }
        }

        movieItem.setPrice(apiMovieItem.getPrice());

        if(apiMovieItem.getMovie() !=null && apiMovieItem.getExposition() != null){
            Exposition exposition = new Exposition()
                    .setId(apiMovieItem.getExposition().getId());
            movieItem.setExposition(exposition);

            Movie movie = new Movie()
                    .setId(apiMovieItem.getMovie().getId());
            movieItem.setMovie(movie);
        }

        movieItemService.save(movieItem);
    }



    @PatchMapping("/movie-item/{id}")
    public void updateMovieItem(@PathVariable Long id, @RequestBody ApiMovieItem apiMovieItem) {
        MovieItem movieItem = movieItemService.findById(id);

        if (movieItem == null){
            throw new NotFoundException("Movie item not found");
        }

        movieItem.setPrice(apiMovieItem.getPrice());
        movieItemService.save(movieItem);
    }



    @GetMapping("/movie-items")
    public List<ApiMovieItem> getAllMovieItems(
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Long expositionId,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Integer lengthFrom,
            @RequestParam(required = false) Integer lengthTo,
            @RequestParam(required = false) String movieNameLike,
            @RequestParam(required = false)MovieItemSort movieItemSort){

        final MovieItemSearch search = new MovieItemSearch();

        search.setPrice(price);
        search.setGenre(genre);
        search.setMovieLengthTo(lengthTo);
        search.setMovieNameLike(movieNameLike);
        search.setMovieLengthFrom(lengthFrom);
        search.setMovieItemSort(movieItemSort);


        if(expositionId != null){
            final Exposition exposition = expositionService.findById(expositionId);
            if (exposition == null){
                throw new NotFoundException("Exposition not found");
            }
            search.setExposition(exposition);
        }

        final List<MovieItem> movieItems = movieItemService.findAll(search);
        return movieItems.stream().map(ApiMovieItem::new).collect(Collectors.toList());
    }



    @GetMapping("/movie-item/{id}")
    public ApiMovieItem getMovieItem(@PathVariable Long id){
        MovieItem movieItem = movieItemService.findById(id);

        return new ApiMovieItem(movieItem);
    }

    @DeleteMapping("/movie-item/{id}")
    public void softDeleteMovieItem(@PathVariable Long id){
        movieItemService.softDelete(id);
    }
}

