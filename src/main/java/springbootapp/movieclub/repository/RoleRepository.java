package springbootapp.movieclub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.movieclub.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAll(Specification<Role> spec);


}
