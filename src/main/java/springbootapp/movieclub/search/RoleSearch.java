package springbootapp.movieclub.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.enums.Privilege;
import springbootapp.movieclub.entity.enums.RoleSort;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RoleSearch {

    private String nameLike;
    private Privilege privilege;
    private RoleSort roleSort;

}
