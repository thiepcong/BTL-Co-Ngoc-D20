package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RevenueDTO {
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String province;
    private String district;
    private String ward;
    private Long waterUsageNumber;
    private String status;
    private Long moneyNumber;

    public RevenueDTO(Long customerId, String customerName, String customerPhone, String customerEmail, String province,
                      String district, String ward, Long waterUsageNumber,Long moneyNumber, String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.waterUsageNumber = waterUsageNumber;
        this.moneyNumber = moneyNumber;
        this.status = status;
    }
}
