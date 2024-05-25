package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiActor;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.enums.ActorSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.ActorSearch;
import springbootapp.movieclub.search.ActorSpec;
import springbootapp.movieclub.service.ActorService;
import springbootapp.movieclub.service.PaginationService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;
    private final PaginationService paginationService;


    @PostMapping("/actor")
    public void saveOrUpdateActor(@RequestBody ApiActor apiActor) {
        final Actor actor;

        if (apiActor.getId() == null) {
            actor = new Actor();
        } else {
            actor = actorService.findById(apiActor.getId());
            if (actor == null) {
                throw new NotFoundException("Actor not found");
            }
        }
        actor.setFirstName(apiActor.getFirstName());
        actor.setLastName(apiActor.getLastName());
        actor.setBirthDate(apiActor.getBirthDate());

        actorService.save(actor);
    }


    @GetMapping("/actor/{id}")
    public ApiActor getActorById(@PathVariable Long id) {
        final Actor actor = actorService.findById(id);

        if (actor == null) {
            throw new NotFoundException("Actor not found");
        }

        return new ApiActor(actor);
    }

    @GetMapping("/actors")
    public Page<ApiActor> getAllActors(
            @RequestParam(required = false) String firstNameLike,
            @RequestParam(required = false) String lastNameLike,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) LocalDate birthDateFrom,
            @RequestParam(required = false) LocalDate birthDateTo,
            @RequestParam(required = false) ActorSort actorSort,
            @RequestParam(required = false) Integer birthYear,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        final ActorSearch search = new ActorSearch()
                .setFirstNameLike(firstNameLike)
                .setLastNameLike(lastNameLike)
                .setBirthDate(birthDate)
                .setAge(age)
                .setAgeTo(ageTo)
                .setAgeFrom(ageFrom)
                .setBirthDateTo(birthDateTo)
                .setBirthDateFrom(birthDateFrom)
                .setActorSort(actorSort)
                .setBirthYear(birthYear);


        Pageable pageable = PageRequest.of(offset, size);
        Specification<Actor> spec = new ActorSpec(search);
        Page<Actor> actorPage = paginationService.findAll(spec, pageable, Actor.class);

        return actorPage.map(this::mapToApiActor);
    }


    @DeleteMapping("/actor/{id}")
    public void deleteActor(@PathVariable Long id) {
        actorService.softDelete(id);
    }

    private ApiActor mapToApiActor(Actor actor) {
        ApiActor apiActor = new ApiActor();
        apiActor.setId(actor.getId());
        apiActor.setFirstName(actor.getFirstName());
        apiActor.setLastName(actor.getLastName());
        apiActor.setBirthDate(actor.getBirthDate());
        return apiActor;
    }
}
