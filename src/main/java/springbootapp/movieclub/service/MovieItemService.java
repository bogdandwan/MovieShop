package springbootapp.movieclub.service;


import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.search.MovieItemSearch;

import java.util.List;

public interface MovieItemService {

    MovieItem findById(Long id);

    void save(MovieItem movieItem);

    List<MovieItem> findAll(MovieItemSearch search);

    void softDelete(Long id);
}
