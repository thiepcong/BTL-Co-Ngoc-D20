package vn.edu.ptit.sqa.model.reportInfor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
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
    private int customerNumber;
    private float moneyPrice;
    private Long totaMoney;

    public ReportDTO(Long customerId, String customerName, String customerPhone, String customerEmail, String provine,
                     String district, String ward, Long newWaterUsageIndex, Long oldWaterUsageIndex,
                     Date startTime, Date endTime, String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.provine = provine;
        this.district = district;
        this.ward = ward;
        this.newWaterUsageIndex = newWaterUsageIndex;
        this.oldWaterUsageIndex = oldWaterUsageIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public ReportDTO(Long customerId, String customerName, String customerPhone, String customerEmail,
                     String provine, String district, String ward, Long waterUsageNumber,
                     Date startTime, Date endTime, String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.provine = provine;
        this.district = district;
        this.ward = ward;
        this.waterUsageNumber = waterUsageNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
