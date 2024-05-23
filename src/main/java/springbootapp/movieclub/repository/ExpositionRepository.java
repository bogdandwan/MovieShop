package springbootapp.movieclub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.movieclub.entity.Exposition;

import java.util.List;

public interface ExpositionRepository extends JpaRepository<Exposition, Long> {

    List<Exposition> findAll(Specification<Exposition> spec);


//    @Query("select e from Exposition e where e.address like :a")
//    List<Exposition> findExpo(@Param("a") String address);

    //List<Exposition> findByAddressLikeAndCityContainingOrderByCityAsc(String address,String city);
}
