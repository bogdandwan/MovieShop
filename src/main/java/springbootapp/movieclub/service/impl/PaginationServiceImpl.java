package springbootapp.movieclub.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springbootapp.movieclub.service.PaginationService;

import java.util.List;

@Service
public class PaginationServiceImpl<T> implements PaginationService<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<T> findAll(Specification<T> spec, Pageable pageable, Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.where(spec.toPredicate(root, cq, cb));

        TypedQuery<T> query = entityManager.createQuery(cq);
        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<T> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, totalRows);
    }

}
