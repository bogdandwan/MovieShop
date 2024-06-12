package springbootapp.movieclub.service;

import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.search.MovieSearch;

import java.util.List;


public interface MovieService {

    void save(Movie movie);

    Movie findById(Long id);

    List<Movie> findAll(MovieSearch search);

    void softDelete(Long id);


}
