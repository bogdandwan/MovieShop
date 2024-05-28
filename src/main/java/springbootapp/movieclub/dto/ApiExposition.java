package springbootapp.movieclub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootapp.movieclub.entity.Exposition;
import springbootapp.movieclub.entity.MovieItem;

import java.util.List;

@Data
@NoArgsConstructor
public class ApiExposition {

    private Long id;
    private String address;
    private String city;
    private ApiMovie movie;
    private String phoneNumber;
    private String bankAccNumber;

    public ApiExposition(Exposition exposition){
        id=exposition.getId();
        address=exposition.getAddress();
        city=exposition.getCity();
        phoneNumber=exposition.getPhoneNumber();
        bankAccNumber=exposition.getBankAccNumber();
    }
}
