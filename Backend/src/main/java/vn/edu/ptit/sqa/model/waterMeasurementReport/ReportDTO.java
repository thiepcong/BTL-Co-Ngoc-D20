package vn.edu.ptit.sqa.model.waterMeasurementReport;

import lombok.Data;

import java.util.Date;

@Data
public class ReportDTO {
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String provine;
    private String district;
    private String ward;
    private Long waterUsageNumber;
    private Long newWaterUsageIndex;
    private Long oldWaterUsageIndex;
    private Date startTime;
    private Date endTime;
    private String status;

    public ReportDTO(Long customerId, String customerName, String customerPhone, String customerEmail,
                     Long waterUsageNumber, Long newWaterUsageIndex, Long oldWaterUsageIndex,
                     Date startTime, Date endTime, String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.waterUsageNumber = waterUsageNumber;
        this.newWaterUsageIndex = newWaterUsageIndex;
        this.oldWaterUsageIndex = oldWaterUsageIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public ReportDTO(Long customerId, String customerName, String customerPhone, String customerEmail,
                     String provine, String district, String ward, Long waterUsageNumber, Long newWaterUsageIndex,
                     Long oldWaterUsageIndex, Date startTime, Date endTime, String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.provine = provine;
        this.district = district;
        this.ward = ward;
        this.waterUsageNumber = waterUsageNumber;
        this.newWaterUsageIndex = newWaterUsageIndex;
        this.oldWaterUsageIndex = oldWaterUsageIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
