package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiRental;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.Rental;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.entity.enums.RentalSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.exceptions.ValidationException;
import springbootapp.movieclub.search.RentalSearch;
import springbootapp.movieclub.service.ExpositionService;
import springbootapp.movieclub.service.MovieItemService;
import springbootapp.movieclub.service.RentalService;
import springbootapp.movieclub.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final MovieItemService movieItemService;
    private final UserService userService;
    private final ExpositionService expositionService;


    @PostMapping("/rental")
    public void saveRental(@RequestBody ApiRental apiRental) {
        final Rental rental = new Rental();

        rental.setRentalDate(LocalDate.now()).setRentalExpiration(LocalDate.now().plusDays(10));

        if(apiRental.getExposition() != null){
            Exposition exposition = new Exposition()
                    .setId(apiRental.getExposition().getId());
            rental.setExposition(exposition);
        }


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
        Rental rental = rentalService.findById(id);

        if (rental == null) {
            throw new NotFoundException("Rental not found");
        }

        rentalService.updateReturnDate(rental, apiRental.getReturnDate());
    }


    @GetMapping("/rentals")
    public List<ApiRental> getAllRentals(@RequestParam(required = false) Long expositionId,
                                         @RequestParam(required = false) LocalDate rentalDate,
                                         @RequestParam(required = false) LocalDate rentalExpiration,
                                         @RequestParam(required = false) LocalDate returnDate,
                                         @RequestParam(required = false) User client,
                                         @RequestParam(required = false) User worker,
                                         @RequestParam(required = false) Boolean active,
                                         @RequestParam(required = false) RentalSort rentalSort,
                                         @RequestParam(required = false) Integer countMovieItemId){



        final RentalSearch search = new RentalSearch()
                .setRentalDate(rentalDate)
                .setRentalExpiration(rentalExpiration)
                .setReturnDate(returnDate)
                .setClient(client)
                .setWorker(worker)
                .setRentalSort(rentalSort)
                .setCountMovieItemId(countMovieItemId);


        if(expositionId != null){
            final Exposition exposition = expositionService.findById(expositionId);
            if (exposition == null){
                throw new NotFoundException("Exposition not found");
            }
            search.setExposition(exposition);
        }
        if (active != null){
            if(active){
                search.setActive(true);
                search.setReturnDate(null);
            }else {
                search.setActive(false);
            }
        }



        final List<Rental> rentals = rentalService.findAll(search);

        return rentals.stream().map(ApiRental::new).collect(Collectors.toList());
    }

    @GetMapping("rental/{id}")
    public ApiRental getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.findById(id);
        if (rental == null) {
            throw new NotFoundException("Rental not found");
        }
        return new ApiRental(rental);
    }

}
