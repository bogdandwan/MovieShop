package springbootapp.movieclub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.Genre;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAll(Specification<Movie> spec);


}
