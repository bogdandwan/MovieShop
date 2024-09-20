package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.MovieSort;
import springbootapp.movieclub.repository.MovieRepository;
import springbootapp.movieclub.search.MovieSearch;
import springbootapp.movieclub.search.MovieSpec;
import springbootapp.movieclub.service.MovieService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    @Override
    @Transactional
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public List<Movie> findAll(MovieSearch search, Pagination pagination, MovieSort sort) {
        return movieRepository.findAll(new MovieSpec(search), pagination.pageable(buildSort(sort)));
    }

    private Sort buildSort(MovieSort sort) {
        if(sort == null){
            return Sort.by(Sort.Direction.ASC, "id");
        }
        boolean asc = sort.name().contains("ASC");
        String property = switch (sort) {
            case NAME_ASC, NAME_DESC -> "name";
            case YEAR_ASC, YEAR_DESC -> "year";
            case LENGTH_ASC, LENGTH_DESC -> "length";
            default -> "id";
        };
        return Sort.by(asc ? Sort.Direction.ASC: Sort.Direction.DESC,property);
    }

    @Override
    public void softDelete(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);

        if (movie != null) {
            movie.setDeletionTime(LocalDate.now());
            movieRepository.save(movie);
        }
    }
}
