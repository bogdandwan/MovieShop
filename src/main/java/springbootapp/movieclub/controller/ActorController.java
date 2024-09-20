package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiActor;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.enums.ActorSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.ActorSearch;
import springbootapp.movieclub.service.ActorService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

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
    public List<ApiActor> getAllActors(
            @RequestParam(required = false) String firstNameLike,
            @RequestParam(required = false) String lastNameLike,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) LocalDate birthDateFrom,
            @RequestParam(required = false) LocalDate birthDateTo,
            @RequestParam(required = false) ActorSort sort,
            @RequestParam(required = false) Integer birthYear,
            Pagination pagination) {

        final ActorSearch search = new ActorSearch()
                .setFirstNameLike(firstNameLike)
                .setLastNameLike(lastNameLike)
                .setBirthDate(birthDate)
                .setAge(age)
                .setAgeTo(ageTo)
                .setAgeFrom(ageFrom)
                .setBirthDateTo(birthDateTo)
                .setBirthDateFrom(birthDateFrom)
                .setActorSort(sort)
                .setBirthYear(birthYear);


        final List<Actor> actors = actorService.findAll(search, pagination, sort);

        return actors.stream().map(ApiActor::new).collect(Collectors.toList());
    }


    @DeleteMapping("/actor/{id}")
    public void deleteActor(@PathVariable Long id) {
        actorService.softDelete(id);
    }

}
