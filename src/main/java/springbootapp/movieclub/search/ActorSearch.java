package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.enums.ActorSort;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ActorSearch {

    private String firstNameLike;
    private String lastNameLike;
    private LocalDate birthDate;
    private Integer age;
    private Integer ageFrom;
    private Integer ageTo;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private Integer birthYear;
    private ActorSort actorSort;

}
