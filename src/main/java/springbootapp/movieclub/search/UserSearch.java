package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.Role;
import springbootapp.movieclub.entity.enums.UserSort;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserSearch {
    private String firstNameLike;
    private String lastNameLike;
    private String username;
    private Role role;
    private UserSort userSort;
}
