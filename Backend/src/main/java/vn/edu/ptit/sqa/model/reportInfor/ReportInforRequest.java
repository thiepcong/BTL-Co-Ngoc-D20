package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;

import java.util.Date;

@Data
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
