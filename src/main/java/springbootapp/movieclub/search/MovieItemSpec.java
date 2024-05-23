package springbootapp.movieclub.search;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.Movie;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.enums.MovieItemSort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MovieItemSpec implements Specification<MovieItem> {

    final MovieItemSearch search;

    @Override
    public Predicate toPredicate(Root<MovieItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (search.getPrice() != null) {
            predicates.add(criteriaBuilder.equal(root.get("price"), search.getPrice()));
        }
        if (search.getExposition() != null && search.getExposition().getId() != null) {
            Join<MovieItem, Exposition> expositionJoin = root.join("exposition");
            predicates.add(criteriaBuilder.equal(expositionJoin.get("id"), search.getExposition().getId()));
        }
        if (search.getGenre() != null) {
            Join<MovieItem, Movie> movieJoin = root.join("movie");

            predicates.add(criteriaBuilder.equal(movieJoin.get("genre"), search.getGenre()));
        }
        if (search.getMovieLengthTo() != null){
            Join<MovieItem, Movie> movieJoin = root.join("movie");
            predicates.add(criteriaBuilder.lessThan(movieJoin.get("length"), search.getMovieLengthTo()));
        }
        if (search.getMovieLengthFrom() !=null){
            Join<MovieItem, Movie> movieJoin = root.join("movie");
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(movieJoin.get("length"), search.getMovieLengthFrom()));
        }
        if (search.getMovieNameLike() != null){
            Join<MovieItem, Movie> movieJoin = root.join("movie");
            predicates.add(criteriaBuilder.like(movieJoin.get("name"), "%" + search.getMovieNameLike() + "%"));
        }

        predicates.add(criteriaBuilder.isNull(root.get("deletionTime")));


        //Sortiranje
        if(search.getMovieItemSort() != null){
            if (search.getMovieItemSort() == MovieItemSort.PRICE_ASC){
                query.orderBy(criteriaBuilder.asc(root.get("price")));
            }
        }
        if(search.getMovieItemSort() != null){
            if (search.getMovieItemSort() == MovieItemSort.PRICE_DESC){
                query.orderBy(criteriaBuilder.desc(root.get("price")));
            }
        }
        if (search.getMovieItemSort() != null){
            if (search.getMovieItemSort() == MovieItemSort.NAME_ASC){
                Join<MovieItem, Movie> movieJoin = root.join("movie");
                query.orderBy(criteriaBuilder.asc(movieJoin.get("name")));
            }
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
