package springbootapp.movieclub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiUser;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.UserSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.UserSearch;
import springbootapp.movieclub.search.UserSpec;
import springbootapp.movieclub.service.PaginationService;
import springbootapp.movieclub.service.RoleService;
import springbootapp.movieclub.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController {


    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final PaginationService<User> paginationService;





    @PreAuthorize("hasAuthority('USER_W')")
    @PostMapping("/user")
    public void saveAndUpdateUser(@RequestBody @Valid ApiUser apiUser) {

        final User user;

        if (apiUser.getId() != null) {
            user = userService.findById(apiUser.getId());
            if (user == null) {
                throw new NotFoundException("Movie not found");
            }
        } else {
            user = new User();
        }

        user.setUsername(apiUser.getUsername());
        user.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        user.setFirstName(apiUser.getFirstName());
        user.setLastName(apiUser.getLastName());
        if (apiUser.getRole() != null) {
            final Role role = roleService.findById(apiUser.getRole().getId());
            if (role == null) {
                throw new NotFoundException("Role not found");
            }
            user.setRole(role);

            userService.save(user);
        }
    }


    @PreAuthorize("hasAuthority('USER_R')")
    @GetMapping("/user/{id}")
    public ApiUser getUserById(@PathVariable Long id){
        final User user = userService.findById(id);
        if(user == null) {
            throw new NotFoundException("User not found");
        }
        return new ApiUser(user);
    }


    @PreAuthorize("hasAuthority('USER_R')")
    @GetMapping("/users")
    public Page<ApiUser> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) UserSort userSort,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {

        final UserSearch search = new UserSearch()
                .setFirstNameLike(firstName)
                .setLastNameLike(lastName)
                .setUsername(username)
                .setUserSort(userSort);
        if (roleId != null){
            final  Role role = roleService.findById(roleId);
            if (role == null){
                throw new NotFoundException("Role not found");
            }
            search.setRole(role);
        }
        Pageable pageable = PageRequest.of(offset, limit);
        Specification<User> spec = new UserSpec(search);
        Page<User> userPage = paginationService.findAll(spec, pageable, User.class);

        return userPage.map(this::mapToApiUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.softDelete(id);
    }

    public ApiUser mapToApiUser(User user) {
        ApiUser apiUser = new ApiUser();
        apiUser.setId(user.getId());
        apiUser.setUsername(user.getUsername());
        apiUser.setFirstName(user.getFirstName());
        apiUser.setLastName(user.getLastName());
        apiUser.setRole(apiUser.getRole());
        return apiUser;
    }
}














