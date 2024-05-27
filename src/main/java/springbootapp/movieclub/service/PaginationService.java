package springbootapp.movieclub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PaginationService<T> {

    Page<T> findAll(Specification<T> spec, Pageable pageable, Class<T> entityClass);

}
