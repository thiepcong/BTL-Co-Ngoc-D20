package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RevenueDTO {
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String province;
    private String district;
    private String ward;
    private Long waterUsageNumber;
    private Long newWaterUsageIndex;
    private Long oldWaterUsageIndex;
    private Date start;
    private Date end;
    private String status;
    private float moneyNumber;

    public RevenueDTO(Long customerId, String customerName, String customerPhone, String customerEmail, String province,
                      String district, String ward, Long newWaterUsageIndex, Long oldWaterUsageIndex, Date start, Date end,
                      String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.newWaterUsageIndex = newWaterUsageIndex;
        this.oldWaterUsageIndex = oldWaterUsageIndex;
        this.start = start;
        this.end = end;
        this.status = status;
    }
}
