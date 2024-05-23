package springbootapp.movieclub.search;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class RoleSpec implements Specification<Role> {

    final RoleSearch search;


    @Override
    public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (search.getPrivilege() != null) {
            Expression<Collection<Privilege>> privileges = root.get("privileges");
            predicates.add(criteriaBuilder.isMember(search.getPrivilege(), privileges));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

