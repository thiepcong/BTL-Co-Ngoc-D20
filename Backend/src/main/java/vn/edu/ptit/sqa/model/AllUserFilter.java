package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllUserFilter {
    private Boolean isFilterAll;
    private String city;
    private String district;
    private String ward;
    private String village;
    private String CustomerNameSearchKey;
    private Boolean isPaid;

}
