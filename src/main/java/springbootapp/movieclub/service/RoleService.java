package springbootapp.movieclub.service;

import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.search.RoleSearch;

import java.util.List;

public interface RoleService {
    Role findById(Long id);

    void save(Role role);

    List<Role> findAllRoles();

    List<Role> findAll(RoleSearch search);

}
