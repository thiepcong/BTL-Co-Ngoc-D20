package vn.edu.ptit.sqa.model.reportInfor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ReportInforRequest {
    private String provine;
    private String district;
    private String ward;
    private Date month;
    private String invoiceStatus;
    private String search;
    private int page;
    private int size;
}
