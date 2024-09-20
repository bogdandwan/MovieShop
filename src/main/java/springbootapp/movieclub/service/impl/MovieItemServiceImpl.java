package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.enums.MovieItemSort;
import springbootapp.movieclub.repository.MovieItemRepository;
import springbootapp.movieclub.search.MovieItemSearch;
import springbootapp.movieclub.search.MovieItemSpec;
import springbootapp.movieclub.service.MovieItemService;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
public class MovieItemServiceImpl implements MovieItemService {

    final MovieItemRepository movieItemRepository;

    @Override
    @Transactional(readOnly = true)
    public MovieItem findById(Long id) {
        return movieItemRepository.findById(id).orElse(null);
    }

    @Override
    public void save(MovieItem movieItem) {
        movieItemRepository.save(movieItem);
    }

    @Override
    public List<MovieItem> findAll(MovieItemSearch search, Pagination pagination, MovieItemSort sort) {
        return movieItemRepository.findAll(new MovieItemSpec(search), pagination.pageable(buildSort(sort)));
    }

    private Sort buildSort(MovieItemSort sort) {
        if (sort == null){
            return Sort.by(Sort.Direction.ASC,"id");
        }
        boolean asc = sort.name().contains("ASC");
        String property = switch (sort) {
            case NAME_ASC, NAME_DESC -> "name";
            case PRICE_ASC, PRICE_DESC -> "price";
            case MOVIE_NAME_ASC, MOVIE_NAME_DESC -> "movie.name";
            default -> "id";
        };
        return Sort.by(asc ? Sort.Direction.ASC: Sort.Direction.DESC,property);
    }


    @Override
    public void softDelete(Long id) {
        MovieItem movieItem = movieItemRepository.findById(id).orElse(null);
        if (movieItem != null) {
            movieItem.setDeletionTime(LocalDate.now());
            movieItemRepository.save(movieItem);
        }
    }


}
