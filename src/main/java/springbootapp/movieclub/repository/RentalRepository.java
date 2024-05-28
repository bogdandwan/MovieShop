package springbootapp.movieclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootapp.movieclub.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
