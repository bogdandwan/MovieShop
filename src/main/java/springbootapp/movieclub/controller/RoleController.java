package springbootapp.movieclub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiRole;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.Role;
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
    public void saveRole(@RequestBody ApiRole apiRole) {
        Role role = new Role();
        role.setName(apiRole.getName());
        role.setPrivileges(apiRole.getPrivileges());

        roleService.save(role);
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_W')")
    @PatchMapping("/role/{id}")
    public void updateRole(@PathVariable Long id, @RequestBody @Valid ApiRole apiRole) {
        final Role role = roleService.findById(id);
        if (role == null) {
            throw new NotFoundException("Role not found");
        }
        if (apiRole.getName() != null){
            role.setName(apiRole.getName());
        }
        roleService.save(role);
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_R')")
    @GetMapping("/roles")
    public List<ApiRole> getAllRoles(@RequestParam (required = false) Privilege privilege){
        final RoleSearch search = new RoleSearch()
                .setPrivilege(privilege);
        final List<Role> roles = roleService.findAll(search);
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

}