package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.MovieItem;
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
    public List<MovieItem> findAll(MovieItemSearch search) {

        Specification<MovieItem> spec = new MovieItemSpec(search);

        return movieItemRepository.findAll(spec);
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
