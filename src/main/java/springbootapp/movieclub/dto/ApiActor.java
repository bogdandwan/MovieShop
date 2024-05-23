package springbootapp.movieclub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.Actor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ApiActor {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;



    public ApiActor(Actor actor){
        id=actor.getId();
        firstName=actor.getFirstName();
        lastName=actor.getLastName();
        birthDate=actor.getBirthDate();
    }
}
