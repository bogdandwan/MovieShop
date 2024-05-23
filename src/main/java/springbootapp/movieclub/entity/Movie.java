package springbootapp.movieclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;
import springbootapp.movieclub.entity.enums.Genre;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "movie")
@Accessors(chain = true)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name",nullable = false)
    private String name;


    @Column(name = "year",nullable = false)
    @Positive
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre",nullable = false)
    private Genre genre;


    @Column(name = "length",nullable = false)
    @Positive
    private Integer length;


    @Column(name = "deletion_time")
    private LocalDate deletionTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

}
