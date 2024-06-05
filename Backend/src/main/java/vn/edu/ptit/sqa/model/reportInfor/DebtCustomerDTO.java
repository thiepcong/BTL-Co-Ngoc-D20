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
public class DebtCustomerDTO {
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
    private String status;
    private Date startTime;
    private Date endTime;
    private int customerNumber;
    private float debtMoneyNumber;

    public DebtCustomerDTO(Long customerId, String customerName, String customerPhone, String customerEmail,
                           String provine, String district, String ward, Long newWaterUsageIndex,
                           Long oldWaterUsageIndex, Date startTime, Date endTime, String status) {
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

    public DebtCustomerDTO() {

    }
}
