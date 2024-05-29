package springbootapp.movieclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Table(name = "exposition")
@Data
@Entity
@Accessors(chain = true)
public class Exposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "exposition",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MovieItem> movieItems;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank
    @Column(name = "bank_acc_number")
    private String bankAccNumber;

    @Column(name = "deletion_time")
    private LocalDate deletionTime;

    @OneToMany(mappedBy = "exposition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;

}
