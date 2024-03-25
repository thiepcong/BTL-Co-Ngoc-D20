package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;

import java.util.Date;

@Data
public class DebtCustomerDTO {
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String provine;
    private String district;
    private String ward;
    private Long waterUsageNumber;
    private String status;
    private int customerNumber;
    private Long debtMoneyNumber;

    public DebtCustomerDTO(Long customerId, String customerName, String customerPhone, String customerEmail,
                           String provine, String district, String ward, Long waterUsageNumber, String status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.provine = provine;
        this.district = district;
        this.ward = ward;
        this.waterUsageNumber = waterUsageNumber;
        this.status = status;
    }
}
