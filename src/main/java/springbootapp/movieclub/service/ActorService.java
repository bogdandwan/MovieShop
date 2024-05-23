package springbootapp.movieclub.service;

import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.search.ActorSearch;

import java.util.List;

public interface ActorService {

    void save(Actor actor);

    Actor findById(Long id);

    List<Actor> findAll(ActorSearch search);

    void softDelete(Long id);

}
