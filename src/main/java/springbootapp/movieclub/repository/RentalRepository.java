package springbootapp.movieclub.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.Rental;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAll(Specification<Rental> spec, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Rental r SET r.returnDate = ?2 WHERE r.id = ?1")
    void updateReturnDate(Long id, LocalDate returnDate);

    @Query("SELECT r FROM Rental r JOIN r.movieItems mi WHERE mi.id = :movieItemId AND r.returnDate IS NULL")
    List<Rental> findActiveRentalsByMovieItemId(@Param("movieItemId") Long movieItemId);

    @Query("SELECT r FROM Rental r WHERE r.client.id = :clientId AND r.returnDate IS NULL")
    List<Rental> findActiveRentalsByClientId(@Param("clientId") Long clientId);


}
