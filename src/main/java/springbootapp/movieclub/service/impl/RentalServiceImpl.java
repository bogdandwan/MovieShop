package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.repository.RentalRepository;
import springbootapp.movieclub.search.ActorSpec;
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
