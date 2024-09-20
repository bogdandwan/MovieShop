package springbootapp.movieclub.service;


import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.enums.MovieItemSort;
import springbootapp.movieclub.search.MovieItemSearch;

import java.util.List;

public interface MovieItemService {

    MovieItem findById(Long id);

    void save(MovieItem movieItem);

    List<MovieItem> findAll(MovieItemSearch search, Pagination pagination, MovieItemSort sort);

    void softDelete(Long id);
}
