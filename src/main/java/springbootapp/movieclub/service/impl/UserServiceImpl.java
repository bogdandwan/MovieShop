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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

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
    public List<User> findAll(UserSearch search) {
        return userRepository.findAll(new UserSpec(search));
    }

    @Override
    public void softDelete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeletionTime(LocalDateTime.now());
            userRepository.save(user);
        }
    }

}

