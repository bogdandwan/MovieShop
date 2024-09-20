package springbootapp.movieclub.service;

import springbootapp.movieclub.dto.ApiUser;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.UserSort;
import springbootapp.movieclub.search.UserSearch;

import java.util.List;

public interface UserService {


    void save(User user);

    User findById(Long id);

    void softDelete(Long id);

    List<User> findAll(UserSearch search, Pagination pagination, UserSort sort);
}
