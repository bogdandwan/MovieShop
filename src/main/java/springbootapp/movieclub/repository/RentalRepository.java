package springbootapp.movieclub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.Actor;
import springbootapp.movieclub.entity.Rental;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAll(Specification<Rental> spec);

    @Modifying
    @Transactional
    @Query("UPDATE Rental r SET r.returnDate = :returnDate WHERE r.id = :id")
    void updateReturnDate(@Param("id") Long id, @Param("returnDate") LocalDate returnDate);

}
