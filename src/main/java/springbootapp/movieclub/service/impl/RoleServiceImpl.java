package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.repository.RoleRepository;
import springbootapp.movieclub.search.RoleSearch;
import springbootapp.movieclub.search.RoleSpec;
import springbootapp.movieclub.service.RoleService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void softDelete(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            role.setDeletionTime(LocalDate.now());
            roleRepository.save(role);
        }
    }

    @Override
    public List<Role> findAll(RoleSearch search) {
        return roleRepository.findAll(new RoleSpec(search));
    }
}
