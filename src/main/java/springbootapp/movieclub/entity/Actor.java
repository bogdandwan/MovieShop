package springbootapp.movieclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "actor")
@Accessors(chain = true)
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "deletion_time")
    private LocalDate deletionTime;


}
