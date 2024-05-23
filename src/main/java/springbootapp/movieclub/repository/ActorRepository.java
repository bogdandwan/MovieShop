package springbootapp.movieclub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.movieclub.entity.Actor;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findAll(Specification<Actor> spec);


}
