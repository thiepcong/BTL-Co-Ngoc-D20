package vn.edu.ptit.sqa.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceScaleRequest {

    @NotNull @NotBlank
    private Integer startIndex;

    @NotNull @NotBlank
    private Integer endIndex;

    @NotNull @NotBlank
    private float price;
}
