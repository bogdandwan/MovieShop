package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiMovieItem;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.enums.Genre;
import springbootapp.movieclub.entity.enums.MovieItemSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.MovieItemSearch;
import springbootapp.movieclub.search.MovieItemSpec;
import springbootapp.movieclub.service.ExpositionService;
import springbootapp.movieclub.service.MovieItemService;
import springbootapp.movieclub.service.MovieService;
import springbootapp.movieclub.service.PaginationService;

@RestController
@RequiredArgsConstructor
public class MovieItemController {

    private final MovieItemService movieItemService;
    private final ExpositionService expositionService;
    private final PaginationService<MovieItem> paginationService;
    private final MovieService movieService;



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

        if(apiMovieItem.getExposition() != null) {
            Exposition exposition = new Exposition()
                    .setId(apiMovieItem.getExposition().getId());
            movieItem.setExposition(exposition);
        }else {
            movieItem.setExposition(null);
        }

        if (apiMovieItem.getMovie() != null) {
            Movie movie = movieService.findById(apiMovieItem.getMovie().getId());
            if (movie == null) {
                throw new NotFoundException("Movie not found");
            }
            movieItem.setMovie(movie);
        }
        if (movieItem.getExposition() == null) {
            movieItem.setAvailable(false);
        } else {
            movieItem.setAvailable(apiMovieItem.getAvailable());
        }

        movieItemService.save(movieItem);
    }


    @GetMapping("/movie-items")
    public Page<ApiMovieItem> getAllMovieItems(
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Long expositionId,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Integer lengthFrom,
            @RequestParam(required = false) Integer lengthTo,
            @RequestParam(required = false) String movieNameLike,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) MovieItemSort movieItemSort,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {

        final MovieItemSearch search = new MovieItemSearch();

        search.setPrice(price);
        search.setGenre(genre);
        search.setMovieLengthTo(lengthTo);
        search.setMovieNameLike(movieNameLike);
        search.setMovieLengthFrom(lengthFrom);
        search.setMovieItemSort(movieItemSort);
        search.setAvailable(available);




        if(expositionId != null){
            final Exposition exposition = expositionService.findById(expositionId);
            if (exposition == null){
                throw new NotFoundException("Exposition not found");
            }
            search.setExposition(exposition);
        }

        Pageable pageable = PageRequest.of(offset, limit);
        Specification<MovieItem> spec = new MovieItemSpec(search);
        Page<MovieItem> movieItemPage = paginationService.findAll(spec, pageable, MovieItem.class);


        return movieItemPage.map(this::mapToApiMovieItem);
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

    public ApiMovieItem mapToApiMovieItem(MovieItem movieItem){
        ApiMovieItem apiMovieItem = new ApiMovieItem();
        apiMovieItem.setId(movieItem.getId());
        apiMovieItem.setPrice(movieItem.getPrice());
        apiMovieItem.setAvailable(movieItem.getAvailable());
        return apiMovieItem;
    }
}

