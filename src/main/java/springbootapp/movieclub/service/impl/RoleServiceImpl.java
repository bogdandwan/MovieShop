package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.enums.RoleSort;
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
    public List<Role> findAll(RoleSearch search, Pagination pagination, RoleSort sort) {
        return roleRepository.findAll(new RoleSpec(search), pagination.pageable(buildSort(sort)));
    }

    private Sort buildSort(RoleSort sort) {
        if (sort == null){
            return Sort.by(Sort.Direction.ASC, "id");
        }
        boolean asc = sort.name().contains("ASC");
        String property = switch (sort){
            case NAME_ASC, NAME_DESC -> "name";
            default -> "id";
        };
        return Sort.by(asc ? Sort.Direction.ASC: Sort.Direction.DESC,property);
        }
}

