package springbootapp.movieclub.search;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.enums.ActorSort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ActorSpec implements Specification<Actor> {

    private final ActorSearch search;


    @Override
    public Predicate toPredicate(Root<Actor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates  = new ArrayList<>();

        if (search.getFirstNameLike() != null){
            predicates.add(criteriaBuilder.like(root.get("firstName"),"%"+search.getFirstNameLike()+"%"));
        }
        if (search.getLastNameLike() != null){
            predicates.add(criteriaBuilder.like(root.get("lastName"),"%"+search.getLastNameLike()+"%"));
        }
        if (search.getBirthDate() != null){
            predicates.add(criteriaBuilder.equal(root.get("birthDate"),search.getBirthDate()));
        }
        if (search.getAge() != null){
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int age = search.getAge();
            int birthYear = currentYear - age;

            LocalDate startDate = LocalDate.of(birthYear, 1, 1);
            LocalDate endDate = LocalDate.of(birthYear, 12, 31);

            predicates.add(criteriaBuilder.between(root.get("birthDate"), startDate, endDate));
        }
        if (search.getAgeTo() != null){
            Integer ageTo = search.getAgeTo();
                LocalDate currentDate = LocalDate.now();
                int currentYear = currentDate.getYear();
                int birthYear = currentYear - ageTo;

                LocalDate startDate = LocalDate.of(birthYear, 1, 1);
                LocalDate endDate = LocalDate.now();

                predicates.add(criteriaBuilder.between(root.get("birthDate"), startDate, endDate));

        }
        if (search.getAgeFrom() != null){
            int ageFrom = search.getAgeFrom();

            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();

            LocalDate endDate = LocalDate.of(currentYear - ageFrom, currentDate.getMonth(), currentDate.getDayOfMonth());

            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), endDate));
        }
        if (search.getBirthDateTo() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), search.getBirthDateTo()));
        }
        if (search.getBirthDateFrom() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), search.getBirthDateFrom()));
        }
        if (search.getBirthYear() != null) {
            int birthYear = search.getBirthYear();
            Expression<Integer> yearExpression = criteriaBuilder.function("YEAR", Integer.class, root.get("birthDate"));
            predicates.add(criteriaBuilder.equal(yearExpression, birthYear));
        }

        predicates.add(criteriaBuilder.isNull(root.get("deletionTime")));


        //Sortiranje
        if (search.getActorSort() != null){
            if (search.getActorSort() == ActorSort.FIRST_NAME_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("firstName")));
            }
        }
        if (search.getActorSort() != null){
            if (search.getActorSort() == ActorSort.LAST_NAME_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("lastName")));
            }
        }
        if (search.getActorSort() != null){
            if (search.getActorSort() == ActorSort.FIRST_NAME_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("firstName")));
            }
        }
        if (search.getActorSort() != null){
            if (search.getActorSort() == ActorSort.LAST_NAME_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("lastName")));
            }
        }
        if (search.getActorSort() != null){
            if (search.getActorSort() == ActorSort.BIRTH_DATE_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("birthDate")));
            }
        }
        if (search.getActorSort() != null){
            if (search.getActorSort() == ActorSort.BIRTH_DATE_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("birthDate")));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
