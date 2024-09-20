package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.ExpositionSort;
import springbootapp.movieclub.repository.ExpositionRepository;
import springbootapp.movieclub.search.ExpositionSearch;
import springbootapp.movieclub.search.ExpositionSpec;
import springbootapp.movieclub.service.ExpositionService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpositionServiceImpl implements ExpositionService {

    private final ExpositionRepository expositionRepository;
    private static final Pattern BANK_ACC_NUMBER_PATTERN = Pattern.compile("^\\d{3}-(\\d{13})-\\d{2}$");


    @Override
    public Exposition findById(Long id) {
        return expositionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Exposition> findAll(ExpositionSearch search, Pagination pagination, ExpositionSort sort) {
        return expositionRepository.findAll(new ExpositionSpec(search), pagination.pageable(buildSort(sort)));
    }

    private Sort buildSort(ExpositionSort sort) {
        if(sort == null){
            return Sort.by(Sort.Direction.ASC, "id");
        }
        boolean asc = sort.name().contains("ASC");
        String property = switch (sort) {
            case EXPOSITION_ID_ASC, EXPOSITION_ID_DESC -> "id";
            case CITY_ASC, CITY_DESC -> "city";
            case ADDRESS_ASC, ADDRESS_DESC -> "address";
            default -> "id";
        };
        return Sort.by(asc ? Sort.Direction.ASC: Sort.Direction.DESC,property);

    }

    @Override
    public void save(Exposition exposition) {
        expositionRepository.save(exposition);
    }

    @Override
    public void softDelete(Long id) {
        Exposition exposition = expositionRepository.findById(id).orElse(null);
        if (exposition != null) {
            exposition.setDeletionTime(LocalDate.now());
            expositionRepository.save(exposition);
        }
    }

    @Override
    public String formatBankAccNumber(String bankAccNumber) {
        String[] parts = bankAccNumber.split("-");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Nevalidan format žiro računa!");
        }

        String middlePart = parts[1];
        String middlePartWithZeros = String.format("%013d", Long.parseLong(middlePart));


        return parts[0] + "-" + middlePartWithZeros + "-" + parts[2];
    }
}
