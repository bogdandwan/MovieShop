package springbootapp.movieclub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.repository.ExpositionRepository;
import springbootapp.movieclub.search.ExpositionSearch;
import springbootapp.movieclub.search.ExpositionSpec;
import springbootapp.movieclub.service.ExpositionService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class ExpositionServiceImpl implements ExpositionService {

    private final ExpositionRepository expositionRepository;


    @Override
    public Exposition findById(Long id) {
        return expositionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Exposition> findAll(ExpositionSearch search) {
        return expositionRepository.findAll(new ExpositionSpec(search));
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
}
