package springbootapp.movieclub.service;

import springbootapp.movieclub.entity.Rental;

public interface RentalService {

    void saveRental(Rental rental);

    Rental findById(Long id);

    //long count(//RentalSearch search);
}
