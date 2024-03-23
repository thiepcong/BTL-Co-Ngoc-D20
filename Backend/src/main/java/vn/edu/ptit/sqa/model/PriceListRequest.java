package vn.edu.ptit.sqa.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListRequest {
    @NotNull @NotBlank
    private Date applyDate;
    private List<PriceScaleRequest> listPriceScales;
}
