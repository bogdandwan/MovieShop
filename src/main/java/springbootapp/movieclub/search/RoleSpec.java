package springbootapp.movieclub.search;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.enums.RoleSort;

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

        //sort
        if (search.getRoleSort() != null){
            if(search.getRoleSort() == RoleSort.NAME_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("name")));
            }
        }
        if (search.getRoleSort() != null){
            if(search.getRoleSort() == RoleSort.NAME_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("name")));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

