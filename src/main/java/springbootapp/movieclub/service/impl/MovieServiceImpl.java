package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.Genre;
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
    public List<Movie> findAll(MovieSearch search) {
        return movieRepository.findAll(new MovieSpec(search));
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
