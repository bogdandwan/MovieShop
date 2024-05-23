package springbootapp.movieclub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.User;

import java.util.List;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByUsernameLike(String username);

    List<User> findAllUsersByLastNameLike(String lastName);

    List<User> findAllUsersByFirstNameLike(String firstName);

    List<User> findAllUserByRole(Role id);

    List<User> findAll(Specification<User> spec);

}