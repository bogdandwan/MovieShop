package springbootapp.movieclub.entity;

import jakarta.persistence.*;
import lombok.Data;
import springbootapp.movieclub.entity.enums.Privilege;

import java.util.List;

@Entity
@Table(name = "role")
@Data

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection(targetClass = Privilege.class)
    @JoinTable(name = "role_privilege", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "privilege",columnDefinition = "varchar(255)")
    private List<Privilege> privileges;

    public Role(String name, List<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public Role() {
    }
}
