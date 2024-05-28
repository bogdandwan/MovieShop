package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiRental;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.exceptions.ValidationException;
import springbootapp.movieclub.service.MovieItemService;
import springbootapp.movieclub.service.RentalService;
import springbootapp.movieclub.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final MovieItemService movieItemService;
    private final UserService userService;


    @PostMapping("/rental")
    public void saveRental(@RequestBody ApiRental apiRental) {
        final Rental rental = new Rental();

        rental.setRentalDate(apiRental.getRentalDate()).setRentalExpiration(apiRental.getRentalExpiration());


        if (apiRental.getMovieItems() != null && !apiRental.getMovieItems().isEmpty()) {
            List<MovieItem> movieItems = apiRental.getMovieItems().stream().map(apiMovieItem -> {
                MovieItem movieItem = movieItemService.findById(apiMovieItem.getId());
                if (movieItem == null) {
                    throw new NotFoundException("Movie item not found");
                }
                return movieItem;
            }).collect(Collectors.toList());
            rental.setMovieItems(movieItems);
        }else {
            throw new ValidationException("Movie items can't be empty");
        }

        if (apiRental.getClient() != null) {
            User client = userService.findById(apiRental.getClient().getId());
            if (client == null) {
                throw new NotFoundException("Client not found");
            }
            rental.setClient(client);
        }

        if (apiRental.getWorker() != null) {
            User worker = userService.findById(apiRental.getWorker().getId());
            if (worker == null) {
                throw new NotFoundException("Worker not found");
            }
            rental.setWorker(worker);
        }

        rentalService.saveRental(rental);
    }

    @PatchMapping("/rental/{id}")
    public void saveReturnDate(@PathVariable Long id, @RequestBody ApiRental apiRental) {
        Rental rental = rentalService.findById(apiRental.getId());

        if (rental == null) {
            throw new NotFoundException("Rental not found");
        }

        //rentalService.updateReturnDate(rental, apiRental.getReturnDate());

    }

}
