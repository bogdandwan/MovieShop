package springbootapp.movieclub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.Role;

import java.util.List;

@NoArgsConstructor
@Data
public class ApiRole {



    private Long id;

    private String name;

    private List<Privilege> privileges;

    public ApiRole(Role role) {
        id = role.getId();
        name = role.getName();
        privileges = role.getPrivileges();
    }
}
