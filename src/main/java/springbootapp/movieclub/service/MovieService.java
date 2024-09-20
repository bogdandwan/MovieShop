package springbootapp.movieclub.service;

import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.MovieSort;
import springbootapp.movieclub.search.MovieSearch;

import java.util.List;


public interface MovieService {

    void save(Movie movie);

    Movie findById(Long id);

    List<Movie> findAll(MovieSearch search, Pagination pagination, MovieSort sort);

    void softDelete(Long id);


}
