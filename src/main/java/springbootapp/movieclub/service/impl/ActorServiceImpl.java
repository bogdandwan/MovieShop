package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.enums.ActorSort;
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
    public List<Actor> findAll(ActorSearch search, Pagination pagination, ActorSort sort) {
        return actorRepository.findAll(new ActorSpec(search), pagination.pageable(buildSort(sort)));
    }

    private Sort buildSort(ActorSort sort) {
        if (sort == null) {
            return Sort.by(Sort.Direction.ASC, "id");
        }
        boolean asc = sort.name().contains("ASC");
        String property = switch (sort) {
            case FIRST_NAME_ASC, FIRST_NAME_DESC -> "firstName";
            case LAST_NAME_ASC, LAST_NAME_DESC -> "lastName";
            case BIRTH_DATE_ASC, BIRTH_DATE_DESC -> "birthDate";
            default -> "id";
        };
        return Sort.by(asc ? Sort.Direction.ASC: Sort.Direction.DESC,property);
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
