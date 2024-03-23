package vn.edu.ptit.sqa.model.waterMeasurementReport;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReportInforRequest {
    private String provine;
    private String district;
    private String ward;
    private int page;
    private int size;

}
