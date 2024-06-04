package springbootapp.movieclub.search;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.RentalSort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RentalSpec implements Specification<Rental> {

    private final RentalSearch search;

    @Override
    public Predicate toPredicate(Root<Rental> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (search.getExposition() != null){
            Join<Rental, Exposition> expositionJoin = root.join("exposition");
            predicates.add(criteriaBuilder.equal(expositionJoin.get("id"), search.getExposition().getId()));
        }
        if (search.getRentalDate() != null){
            predicates.add(criteriaBuilder.equal(root.get("rentalDate"), search.getRentalDate()));
        }
        if (search.getRentalExpiration() != null){
            predicates.add(criteriaBuilder.equal(root.get("rentalExpiration"), search.getRentalExpiration()));
        }
        if (search.getReturnDate() != null){
            predicates.add(criteriaBuilder.equal(root.get("returnDate"), search.getReturnDate()));
        }
        if (search.getClient() != null){
            Join<Rental, User> clientJoin = root.join("client");
            predicates.add(criteriaBuilder.equal(clientJoin.get("id"), search.getClient().getId()));
        }
        if (search.getWorker() != null){
            Join<Rental, User> workerJoin = root.join("worker");
            predicates.add(criteriaBuilder.equal(workerJoin.get("id"), search.getWorker().getId()));
        }
        if (search.getActive() != null) {
            if (search.getActive()) {
                predicates.add(criteriaBuilder.isNull(root.get("returnDate")));
            } else {
                predicates.add(criteriaBuilder.isNotNull(root.get("returnDate")));
            }
        }
        if (search.getRentalSort() != null){
            if (search.getRentalSort() == RentalSort.RENTAL_DATE_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("rentalDate")));
            }
            if (search.getRentalSort() == RentalSort.RENTAL_DATE_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("rentalDate")));
            }
            if (search.getRentalSort() == RentalSort.RENTAL_EXPIRATION_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("rentalExpiration")));
            }
            if (search.getRentalSort() == RentalSort.RENTAL_EXPIRATION_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("rentalExpiration")));
            }
            if (search.getRentalSort() == RentalSort.RETURN_DATE_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("returnDate")));
            }
            if (search.getRentalSort() == RentalSort.RETURN_DATE_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("returnDate")));
            }
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
