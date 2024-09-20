package springbootapp.movieclub.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootapp.movieclub.entity.MovieItem;

import java.util.List;

@Repository
public interface MovieItemRepository extends JpaRepository<MovieItem, Long> {

    List<MovieItem> findAll(Specification<MovieItem> spec, Pageable pageable);

}
