package springbootapp.movieclub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiRole;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.enums.RoleSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.RoleSearch;
import springbootapp.movieclub.search.RoleSpec;
import springbootapp.movieclub.service.PaginationService;
import springbootapp.movieclub.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PaginationService<Role> paginationService;

    //@PreAuthorize("hasAnyAuthority('ROLE_W')")
    @PostMapping("/role")
    public void saveAndUpdateRole(@RequestBody ApiRole apiRole) {

        final Role role;

        if(apiRole.getId() != null){
            role = roleService.findById(apiRole.getId());
            if(role == null){
                throw new NotFoundException("Role not found");
            }
        }else {
            role = new Role();
        }

        if (apiRole.getName() != null){
            role.setName(apiRole.getName());
        }
        role.setPrivileges(apiRole.getPrivileges());

        roleService.save(role);

    }

    //@PreAuthorize("hasAnyAuthority('ROLE_R')")
    @GetMapping("/roles")
    public Page<ApiRole> getAllRoles(@RequestParam (required = false) Privilege privilege,
                                     @RequestParam (required = false) String nameLike,
                                     @RequestParam (required = false) RoleSort roleSort,
                                     @RequestParam (required = false, defaultValue = "0") Integer offset,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit){
        final RoleSearch search = new RoleSearch()
                .setPrivilege(privilege)
                .setNameLike(nameLike)
                .setRoleSort(roleSort);


        Pageable pageable = PageRequest.of(offset, limit);
        Specification<Role> spec = new RoleSpec(search);
        Page<Role> rolePage = paginationService.findAll(spec, pageable, Role.class);

        return rolePage.map(this::mapToApiRole);



    }

    //@PreAuthorize("hasAnyAuthority('ROLE_R')")
    @GetMapping("/role/{id}")
    public ApiRole getRoleById(@PathVariable Long id){
        Role role = roleService.findById(id);

        if (role == null){
            throw new NotFoundException("Role not found");
        }

        return new ApiRole(role);
    }

    @DeleteMapping("/role/{id}")
    public void deleteRole(@PathVariable Long id){
        roleService.softDelete(id);
    }

    public ApiRole mapToApiRole(Role role){
        ApiRole apiRole = new ApiRole();
        apiRole.setId(role.getId());
        apiRole.setName(role.getName());
        apiRole.setPrivileges(role.getPrivileges());
        return apiRole;
    }

}