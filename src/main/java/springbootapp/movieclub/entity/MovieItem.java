package springbootapp.movieclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "movie_item")
@Accessors(chain = true)
public class MovieItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "deletion_time")
    private LocalDate deletionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exposition_id")
    private Exposition exposition;

}
