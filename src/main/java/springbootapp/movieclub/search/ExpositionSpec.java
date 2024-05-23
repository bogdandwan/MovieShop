package springbootapp.movieclub.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.ExpositionSort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ExpositionSpec implements Specification<Exposition> {

    private final ExpositionSearch search;

    @Override
    public Predicate toPredicate(Root<Exposition> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates  = new ArrayList<>();
        if (search.getAddressLike() != null){
            predicates.add(criteriaBuilder.like(root.get("address"),"%"+search.getAddressLike()+"%"));
        }
        if (search.getCityLike() != null){
            predicates.add(criteriaBuilder.like(root.get("city"),"%"+search.getCityLike()+"%"));
        }
        if (search.getPhoneNumber() != null){
            predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + search.getPhoneNumber() + "%"));
        }
        if (search.getBankAccNumberLike() != null){
            predicates.add(criteriaBuilder.like(root.get("bankAccNumber"), "%" + search.getBankAccNumberLike() + "%"));
        }

        predicates.add(criteriaBuilder.isNull(root.get("deletionTime")));


        //Sortiranje
        if (search.getExpositionSort() != null){
            if (search.getExpositionSort() == ExpositionSort.EXPOSITION_ID_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("id")));
            }
        }
        if (search.getExpositionSort() != null){
            if (search.getExpositionSort() == ExpositionSort.EXPOSITION_ID_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }
        }
        if (search.getExpositionSort() != null){
            if (search.getExpositionSort() == ExpositionSort.CITY_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("city")));
            }
        }
        if (search.getExpositionSort() != null){
            if (search.getExpositionSort() == ExpositionSort.CITY_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("city")));
            }
        }
        if (search.getExpositionSort() != null){
            if (search.getExpositionSort() == ExpositionSort.ADDRESS_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("address")));
            }
        }
        if (search.getExpositionSort() != null){
            if (search.getExpositionSort() == ExpositionSort.ADDRESS_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("address")));
            }
        }



        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
