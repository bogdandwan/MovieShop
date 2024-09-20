package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.RentalSort;
import springbootapp.movieclub.repository.RentalRepository;
import springbootapp.movieclub.search.RentalSearch;
import springbootapp.movieclub.search.RentalSpec;
import springbootapp.movieclub.service.RentalService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;


    @Override
    public void saveRental(Rental rental) {

        User client = rental.getClient();

        List<Rental> activeRentalsForClient = rentalRepository.findActiveRentalsByClientId(client.getId());
        if (!activeRentalsForClient.isEmpty()) {
            throw new IllegalStateException("Client with ID " + client.getId() + " already has an active rental");
        }


        for (MovieItem movieItem : rental.getMovieItems()) {
            List<Rental> activeRentals = rentalRepository.findActiveRentalsByMovieItemId(movieItem.getId());
            if (!activeRentals.isEmpty()) {
                throw new IllegalStateException("MovieItem with ID " + movieItem.getId() + " is already rented out");
            }
        }
        rentalRepository.save(rental);
    }

    @Override
    @Transactional(readOnly = true)
    public Rental findById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }


    @Transactional
    @Override
    public void updateReturnDate(Rental rental, LocalDate returnDate) {
        rentalRepository.updateReturnDate(rental.getId(), returnDate);
    }

    @Override
    public List<Rental> findAll(RentalSearch search, Pagination pagination, RentalSort sort) {
        return rentalRepository.findAll(new RentalSpec(search), pagination.pageable(buildSort(sort)));
    }

    private Sort buildSort(RentalSort sort) {
        if (sort == null){
            return Sort.by(Sort.Direction.ASC,"id");
        }
        boolean asc = sort.name().contains("ASC");
        String property = switch (sort) {
            case RENTAL_DATE_ASC, RENTAL_DATE_DESC -> "rentalDate";
            case RENTAL_EXPIRATION_ASC, RENTAL_EXPIRATION_DESC -> "rentalExpiration";
            case RETURN_DATE_ASC, RETURN_DATE_DESC -> "returnDate";
            default -> "id";
        };
        return Sort.by(asc ? Sort.Direction.ASC: Sort.Direction.DESC,property);
    }


}
