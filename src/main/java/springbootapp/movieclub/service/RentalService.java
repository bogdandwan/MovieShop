package springbootapp.movieclub.service;

import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.entity.enums.RentalSort;
import springbootapp.movieclub.search.RentalSearch;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {

    void saveRental(Rental rental);

    Rental findById(Long id);

   // long countActiveRentalsByMovieItemId(Long movieItemId);

    void updateReturnDate(Rental rental, LocalDate returnDate);

    List<Rental> findAll(RentalSearch search, Pagination pagination, RentalSort sort);
}
