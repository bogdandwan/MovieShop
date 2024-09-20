package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiRole;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.enums.RoleSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.RoleSearch;
import springbootapp.movieclub.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

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
    public List<ApiRole> getAllRoles(@RequestParam (required = false) Privilege privilege,
                                     @RequestParam (required = false) String nameLike,
                                     @RequestParam (required = false) RoleSort sort,
                                     Pagination pagination){
        final RoleSearch search = new RoleSearch()
                .setPrivilege(privilege)
                .setNameLike(nameLike)
                .setRoleSort(sort);


        final List<Role> roles = roleService.findAll(search, pagination, sort);

        return roles.stream().map(ApiRole::new).collect(Collectors.toList());



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


}