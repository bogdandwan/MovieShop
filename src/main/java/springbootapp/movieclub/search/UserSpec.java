package springbootapp.movieclub.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.UserSort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserSpec implements Specification<User> {

    private final UserSearch search;
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates  = new ArrayList<>();
        if (search.getFirstNameLike() != null){
            predicates.add(criteriaBuilder.like(root.get("firstName"),"%"+search.getFirstNameLike()+"%"));
        }
        if (search.getLastNameLike() != null){
            predicates.add(criteriaBuilder.like(root.get("lastName"),"%"+search.getLastNameLike()+"%"));
        }
        if (search.getUsername() != null){
            predicates.add(criteriaBuilder.equal(root.get("username"),search.getUsername()));
        }
        if (search.getRole() != null){
            predicates.add(criteriaBuilder.equal(root.get("role"),search.getRole()));
        }

        //Sortiranje
        if (search.getUserSort() != null){
            if (search.getUserSort() == UserSort.FIRST_NAME_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("firstName")));
            }
        }
        if (search.getUserSort() == UserSort.LAST_NAME_ASC){
            query.orderBy(criteriaBuilder.asc(root.get("lastName")));
        }
        if (search.getUserSort() == UserSort.FIRST_NAME_DESC){
            query.orderBy(criteriaBuilder.desc(root.get("firstName")));
        }
        if (search.getUserSort() == UserSort.LAST_NAME_DESC){
            query.orderBy(criteriaBuilder.desc(root.get("lastName")));
        }
        if (search.getUserSort() == UserSort.USERNAME_ASC){
            query.orderBy(criteriaBuilder.asc(root.get("username")));
        }
        if (search.getUserSort() == UserSort.USERNAME_DESC){
            query.orderBy(criteriaBuilder.desc(root.get("username")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
