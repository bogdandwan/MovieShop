package springbootapp.movieclub.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class Pagination {
    private Integer offset;
    private Integer limit;

    public Pageable pageable(Sort sort){
        return PageRequest.of(this.getOffset(),this.getLimit(),sort);
    }

}
