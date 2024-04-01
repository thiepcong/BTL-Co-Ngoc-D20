package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
public class DebtReportResponse {
    private DebtReportDTO debtReportDTO;
    private List<DebtCustomerDTO> debtCustomerList;
    private float totalDebtNumber;
    private PageDto pageDto;
}
