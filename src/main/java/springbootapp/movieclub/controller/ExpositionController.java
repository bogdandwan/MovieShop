package springbootapp.movieclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import springbootapp.movieclub.dto.ApiExposition;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.ExpositionSort;
import springbootapp.movieclub.exceptions.NotFoundException;
import springbootapp.movieclub.search.ExpositionSearch;
import springbootapp.movieclub.search.ExpositionSpec;
import springbootapp.movieclub.service.ExpositionService;
import springbootapp.movieclub.service.PaginationService;

@RestController
@RequiredArgsConstructor
public class ExpositionController {

    private final ExpositionService expositionService;
    private final PaginationService<Exposition> paginationService;



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
    public Page<ApiExposition> getExpositions(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) ExpositionSort expositionSort,
            @RequestParam(required = false) String bankAccNumberLike,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit){

        final ExpositionSearch search = new ExpositionSearch()
                .setAddressLike(address)
                .setCityLike(city)
                .setPhoneNumber(phoneNumber)
                .setBankAccNumberLike(bankAccNumberLike)
                .setExpositionSort(expositionSort);

        Pageable pageable = PageRequest.of(offset, limit);
        Specification<Exposition> spec = new ExpositionSpec(search);
        Page<Exposition> expositionPage = paginationService.findAll(spec,pageable, Exposition.class);


        return expositionPage.map(this::mapToApiExposition);


    }

    @DeleteMapping("exposition/{id}")
    public void softDeleteExposition(@PathVariable Long id){
        expositionService.softDelete(id);
    }

    public ApiExposition mapToApiExposition(Exposition exposition){
        ApiExposition apiExposition = new ApiExposition();
        apiExposition.setId(exposition.getId());
        apiExposition.setAddress(exposition.getAddress());
        apiExposition.setCity(exposition.getCity());
        apiExposition.setPhoneNumber(exposition.getPhoneNumber());
        apiExposition.setBankAccNumber(exposition.getBankAccNumber());
        return apiExposition;
    }

}
