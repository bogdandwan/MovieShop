package springbootapp.movieclub.service;

import org.springframework.data.domain.Sort;
import springbootapp.movieclub.dto.pagination.Pagination;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.enums.ExpositionSort;
import springbootapp.movieclub.search.ExpositionSearch;

import java.util.List;

public interface ExpositionService {

    Exposition findById(Long id);

    List<Exposition> findAll(ExpositionSearch search, Pagination pagination, ExpositionSort sort);

    void save(Exposition exposition);

    void softDelete(Long id);

    String formatBankAccNumber(String bankAccNumber);

}
