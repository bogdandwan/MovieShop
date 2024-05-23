package springbootapp.movieclub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiUser;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.UserSearch;
import springbootapp.movieclub.service.RoleService;
import springbootapp.movieclub.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {


    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;





    @PreAuthorize("hasAuthority('USER_W')")
    @PostMapping("/user")
    public void saveUser(@RequestBody @Valid ApiUser apiUser){

        final User user = new User()
                .setFirstName(apiUser.getFirstName())
                .setLastName(apiUser.getLastName())
                .setUsername(apiUser.getUsername())
                .setPassword(new BCryptPasswordEncoder().encode(apiUser.getPassword()));

            if(apiUser.getRole() != null) {
                final Role role = roleService.findById(apiUser.getRole().getId());
                    if(role == null) {
                        throw new NotFoundException("Role not found");
                    }
                    user.setRole(role);
            }
            userService.save(user);
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




    @PreAuthorize("hasAuthority('USER_W')")
    @PatchMapping("/user/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid ApiUser apiUser) {
        final User user = userService.findById(id);
        if (user == null){
            throw new NotFoundException("User not found");
        }
        if (apiUser.getFirstName() != null){
            user.setFirstName(apiUser.getFirstName());
            user.setLastName(apiUser.getLastName());
            user.setUsername(apiUser.getUsername());
            user.setPassword(apiUser.getPassword());
        }
        userService.save(user);
    }





    @PreAuthorize("hasAuthority('USER_R')")
    @GetMapping("/users")
    public List<ApiUser> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Long roleId) {

        final UserSearch search = new UserSearch()
                .setFirstNameLike(firstName)
                .setLastNameLike(lastName)
                .setUsername(username);
        if (roleId != null){
            final  Role role = roleService.findById(roleId);
            if (role == null){
                throw new NotFoundException("Role not found");
            }
            search.setRole(role);
        }
        final List<User> users = userService.findAll(search);
        return users.stream().map(ApiUser::new).collect(Collectors.toList());
    }
}














//    @GetMapping("/users/username/{username}")
//    public List<ApiUser> getAllUsersByUsername(@PathVariable String username){
//        List<User> users = userService.findAllUsersByUsername(username);
//        if(users.isEmpty()){
//            return new ArrayList<>();
//        }
//        List<ApiUser> apiUsers = users.stream()
//                .map(ApiUser::new).collect(Collectors.toList());
//
//        return apiUsers;
//    }
//
//    @GetMapping("/users/lastName/{lastName}")
//    public List<ApiUser> getAllUsersByLastName(@PathVariable String lastName){
//        List<User> users = userService.findAllUsersByLastName(lastName);
//        if(users.isEmpty()){
//            throw new RuntimeException("User not found");
//        }
//        List<ApiUser> apiUsers = users.stream()
//                .map(ApiUser::new).collect(Collectors.toList());
//
//        return apiUsers;
//    }
//
//    @GetMapping("/users/firstName/{firstName}")
//    public List<ApiUser> getAllUsersByFirstName(@PathVariable String firstName){
//        List<User> users = userService.findAllUsersByFirstName(firstName);
//        if(users.isEmpty()){
//            throw new RuntimeException("User not found");
//        }
//        List<ApiUser> apiUsers = users.stream()
//                .map(ApiUser::new).collect(Collectors.toList());
//
//        return apiUsers;
//    }
//
//    @GetMapping("/users/role/{roleId}")
//    public List<ApiUser> getAllUsersByRole(@PathVariable Role roleId){
//        List<User> users = userService.findAllUsersByRole(roleId);
//        if(users.isEmpty()){
//            throw new RuntimeException("User not found");
//        }
//        List<ApiUser> apiUsers = users.stream()
//                .map(ApiUser::new).collect(Collectors.toList());
//
//        return apiUsers;
//    }



