package springbootapp.movieclub.service;

import springbootapp.movieclub.dto.ApiUser;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.search.UserSearch;

import java.util.List;

public interface UserService {


    void save(User user);

    User findById(Long id);

    List<User> filterUsers(String usernameLike, String firstName, String lastName, Long roleId);

    void updateUser(Long userId, ApiUser apiUser);





    List<User> findAllUsers();

    List<User> findAllUsersByUsername(String username);

    List<User> findAllUsersByLastName(String lastName);

    List<User> findAllUsersByFirstName(String firstName);

    List<User> findAllUsersByRole(Role id);

    List<User> findAll(UserSearch search);
}
