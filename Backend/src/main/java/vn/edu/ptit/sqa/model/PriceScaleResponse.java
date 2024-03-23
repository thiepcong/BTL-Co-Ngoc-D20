package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceScaleResponse {
    private Integer id;

    private Integer startIndex;

    private Integer endIndex;

    private float price;

}
