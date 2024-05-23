package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.repository.ActorRepository;
import springbootapp.movieclub.search.ActorSearch;
import springbootapp.movieclub.search.ActorSpec;
import springbootapp.movieclub.service.ActorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Override
    public void save(Actor actor) {

        actorRepository.save(actor);

    }

    @Override
    public Actor findById(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Actor> findAll(ActorSearch search) {
        return actorRepository.findAll(new ActorSpec(search));
    }

    @Override
    public void softDelete(Long id) {
        Actor actor = actorRepository.findById(id).orElse(null);
        if (actor != null) {
            actor.setDeletionTime(LocalDate.now());
            actorRepository.save(actor);
        }
    }
}
