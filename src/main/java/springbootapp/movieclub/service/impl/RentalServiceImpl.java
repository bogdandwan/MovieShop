package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.repository.RentalRepository;
import springbootapp.movieclub.service.RentalService;

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
}
