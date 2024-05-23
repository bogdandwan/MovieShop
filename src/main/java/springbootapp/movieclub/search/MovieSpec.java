package springbootapp.movieclub.search;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.enums.MovieSort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MovieSpec implements Specification<Movie> {

    private final MovieSearch search;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (search.getNameLike() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + search.getNameLike() + "%"));
        }
        if (search.getYear() != null) {
            predicates.add(criteriaBuilder.equal(root.get("year"), search.getYear()));
        }
        if (search.getGenre() != null) {
            predicates.add(criteriaBuilder.equal(root.get("genre"), search.getGenre()));
        }
        if (search.getLength() != null) {
            predicates.add(criteriaBuilder.equal(root.get("length"), search.getLength()));
        }
        if (search.getGenres() != null && !search.getGenres().isEmpty()) {
            predicates.add(root.get("genre").in(search.getGenres()));
        }
        if (search.getLengthTo() != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("length"), search.getLengthTo()));
        }
        if (search.getLengthFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("length"), search.getLengthFrom()));
        }
        if (search.getActorId() != null) {
            Join<Movie, Actor> actorJoin = root.join("actors", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(actorJoin.get("id"), search.getActorId()));
        }
        if (search.getFullText() != null) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%" + search.getFullText() + "%"),
                    criteriaBuilder.equal(root.get("genre"), search.getFullText())));
        }

        predicates.add(criteriaBuilder.isNull(root.get("deletionTime")));

            //Sortiranje
        if (search.getMovieSort() != null) {
            if (search.getMovieSort() == MovieSort.NAME_ASC) {
                query.orderBy(criteriaBuilder.asc(root.get("name")));
            } else if (search.getMovieSort() == MovieSort.NAME_DESC) {
                query.orderBy(criteriaBuilder.desc(root.get("name")));
            }


            if (search.getMovieSort() == MovieSort.YEAR_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("year")));
            }else if(search.getMovieSort() == MovieSort.YEAR_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("year")));
            }

            if (search.getMovieSort() == MovieSort.LENGTH_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("length")));
            } else if (search.getMovieSort() == MovieSort.LENGTH_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("length")));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
