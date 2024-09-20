package springbootapp.movieclub.service;

import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.enums.ActorSort;
import springbootapp.movieclub.search.ActorSearch;

import java.util.List;

public interface ActorService {

    void save(Actor actor);

    Actor findById(Long id);

    List<Actor> findAll(ActorSearch search, Pagination pagination, ActorSort sort);

    void softDelete(Long id);

}
