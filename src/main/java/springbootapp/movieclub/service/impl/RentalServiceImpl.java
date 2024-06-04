package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.entity.User;
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
    public List<Rental> findAll(RentalSearch search) {
        return rentalRepository.findAll(new RentalSpec(search));
    }

}
