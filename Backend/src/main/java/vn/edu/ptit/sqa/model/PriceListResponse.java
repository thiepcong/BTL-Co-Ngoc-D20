package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListResponse {
    private Integer id;
    private UserTypeResponse userType;
    private Date applyDate;
    private Integer status;
    List<PriceScaleResponse> listPriceScales;
}
