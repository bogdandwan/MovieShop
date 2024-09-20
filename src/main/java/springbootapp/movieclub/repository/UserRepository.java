package springbootapp.movieclub.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.User;

import java.util.List;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAll(Specification<User> spec, Pageable pageable);

}