package springbootapp.movieclub.service;

import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.search.ExpositionSearch;

import java.util.List;

public interface ExpositionService {

    Exposition findById(Long id);

    List<Exposition> findAll(ExpositionSearch search);

    void save(Exposition exposition);

    void softDelete(Long id);

    public String formatBankAccNumber(String bankAccNumber);

}
