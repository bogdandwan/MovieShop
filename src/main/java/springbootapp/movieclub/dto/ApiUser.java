package springbootapp.movieclub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.User;

@Data
@NoArgsConstructor
public class ApiUser {

    public ApiUser(User user) {
        id=user.getId();
        firstName=user.getFirstName();
        lastName = user.getLastName();
        username = user.getUsername();
        if (user.getRole() != null){
            role = new ApiRole(user.getRole());
        }

    }

    private Long id;
    @NotBlank private String firstName;
    private String lastName;
    private String username;
    private String password;
    private ApiRole role;

}
