package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiExposition;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.ExpositionSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.ExpositionSearch;
import springbootapp.movieclub.service.ExpositionService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ExpositionController {

    private final ExpositionService expositionService;
    private static final Pattern BANK_ACC_NUMBER_PATTERN = Pattern.compile("^\\d{3}-(\\d{13})-\\d{2}$");



    @PostMapping("/exposition")
    public void saveOrUpdateExposition(@RequestBody ApiExposition apiExposition){

        final Exposition exposition;

        if (apiExposition.getId() == null){
            exposition = new Exposition();
        }else {
            exposition = expositionService.findById(apiExposition.getId());
            if (exposition == null){
                throw new NotFoundException("Exposition not found");
            }
        }
        exposition.setAddress(apiExposition.getAddress());
        exposition.setCity(apiExposition.getCity());
        exposition.setPhoneNumber(apiExposition.getPhoneNumber());

        String formattedBankAccNumber = expositionService.formatBankAccNumber(apiExposition.getBankAccNumber());
        exposition.setBankAccNumber(formattedBankAccNumber);

        expositionService.save(exposition);
    }


    @GetMapping("/expositions")
    public List<ApiExposition> getExpositions(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) ExpositionSort expositionSort,
            @RequestParam(required = false) String bankAccNumberLike){

        final ExpositionSearch expositionSearch = new ExpositionSearch()
                .setAddressLike(address)
                .setCityLike(city)
                .setPhoneNumber(phoneNumber)
                .setBankAccNumberLike(bankAccNumberLike)
                .setExpositionSort(expositionSort);

        List<Exposition> expositions = expositionService.findAll(expositionSearch);

        return expositions.stream().map(ApiExposition::new).collect(Collectors.toList());


    }

    @DeleteMapping("exposition/{id}")
    public void softDeleteExposition(@PathVariable Long id){
        expositionService.softDelete(id);
    }

}
