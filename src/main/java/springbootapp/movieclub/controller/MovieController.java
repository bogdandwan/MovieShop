package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiMovie;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.Genre;
import springbootapp.movieclub.entity.enums.MovieSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.MovieSearch;
import springbootapp.movieclub.service.ActorService;
import springbootapp.movieclub.service.MovieService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;


   @PostMapping("/movie")
   public void saveOrUpdateMovie(@RequestBody ApiMovie apiMovie) {
       final Movie movie;

       if (apiMovie.getId() != null) {
           movie = movieService.findById(apiMovie.getId());
           if (movie == null) {
               throw new NotFoundException("Movie not found");
           }
       } else {
           movie = new Movie();
       }

       movie.setName(apiMovie.getName());
       movie.setYear(apiMovie.getYear());
       movie.setGenre(apiMovie.getGenre());
       movie.setLength(apiMovie.getLength());

       if (apiMovie.getActors() != null && !apiMovie.getActors().isEmpty()) {
           List<Actor> actors = apiMovie.getActors().stream().map(apiActor -> {
               Actor actor = actorService.findById(apiActor.getId());
               if (actor == null) {
                   throw new NotFoundException("Actor not found");
               }
               return actor;
           }).collect(Collectors.toList());
           movie.setActors(actors);
       } else {
           movie.setActors(null);
       }
       movieService.save(movie);
   }



    @GetMapping("/movie/{id}")
    public ApiMovie getById(@PathVariable Long id){
        final Movie movie = movieService.findById(id);

        if (movie == null){
            throw new NotFoundException("Movie not found");
        }
        return new ApiMovie(movie);
    }


    @GetMapping("/movies")
    public List<ApiMovie> getAllMovies(
            @RequestParam(required = false) String nameLike,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Integer length,
            @RequestParam(required = false) List<Genre> genres,
            @RequestParam(required = false) Integer lengthFrom,
            @RequestParam(required = false) Integer lengthTo,
            @RequestParam(required = false) Long actorId,
            @RequestParam(required = false) String fullText,
            @RequestParam(required = false) MovieSort sort,
            Pagination pagination){


        final MovieSearch search = new MovieSearch()
                .setNameLike(nameLike)
                .setYear(year)
                .setGenre(genre)
                .setLength(length)
                .setGenres(genres)
                .setLengthTo(lengthTo)
                .setLengthFrom(lengthFrom)
                .setActorId(actorId)
                .setFullText(fullText)
                .setMovieSort(sort);

        final List<Movie> movies = movieService.findAll(search, pagination, sort);


        return movies.stream().map(ApiMovie::new).collect(Collectors.toList());

    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres(){
        return  Arrays.asList(Genre.values());
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable Long id){
       movieService.softDelete(id);
    }

}
