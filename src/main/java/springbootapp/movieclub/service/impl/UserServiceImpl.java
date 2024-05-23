package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.dto.ApiUser;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.repository.UserRepository;
import springbootapp.movieclub.search.UserSearch;
import springbootapp.movieclub.search.UserSpec;
import springbootapp.movieclub.service.RoleService;
import springbootapp.movieclub.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleService roleService;

    private User user;

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsersByUsername(String username) {
        return userRepository.findByUsernameLike("%" + username + "%");
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsersByLastName(String lastName) {
        return userRepository.findAllUsersByLastNameLike(lastName);
    }

    @Override
    public List<User> findAllUsersByFirstName(String firstName) {
        return userRepository.findAllUsersByFirstNameLike(firstName);
    }

    @Override
    public List<User> findAllUsersByRole(Role id) {
        return userRepository.findAllUserByRole(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(UserSearch search) {
        return userRepository.findAll(new UserSpec(search));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> filterUsers(String usernameLike, String firstName, String lastName, Long roleId) {

        List<User> users = userRepository.findAll();


        if (usernameLike != null && !usernameLike.isEmpty()) {
            users = users.stream().filter(user -> user.getUsername().contains(usernameLike)).collect(Collectors.toList());
        }

        if (firstName != null && !firstName.isEmpty()) {
            users = users.stream().filter(user -> user.getFirstName().contains(firstName)).collect(Collectors.toList());
        }

        if (lastName != null && !lastName.isEmpty()) {
            users = users.stream().filter(user -> user.getLastName().contains(lastName)).collect(Collectors.toList());
        }

        if (roleId != null) {
            users = users.stream().filter(user -> user.getRole().getId().equals(roleId)).collect(Collectors.toList());
        }

        return users;
    }

    @Override
    @Transactional
    public void updateUser(Long userId, ApiUser apiUser) {
        User user = findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (apiUser.getFirstName() != null) {
            user.setFirstName(apiUser.getFirstName());
        }
        if (apiUser.getLastName() != null) {
            user.setLastName(apiUser.getLastName());
        }
        if (apiUser.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(apiUser.getPassword()));
        }
        if (apiUser.getRole() != null) {
            Role role = roleService.findById(apiUser.getRole().getId());
            if (role == null) {
                throw new NotFoundException("Role not found");
            }
            user.setRole(role);
        }

        save(user);
    }
}

