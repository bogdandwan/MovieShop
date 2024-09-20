package springbootapp.movieclub.service;

import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.enums.RoleSort;
import springbootapp.movieclub.search.RoleSearch;
import springbootapp.movieclub.search.RoleSpec;

import java.util.List;

public interface RoleService {
    Role findById(Long id);

    void save(Role role);

    void softDelete(Long id);

    List<Role> findAll(RoleSearch search, Pagination pagination, RoleSort sort);

}
