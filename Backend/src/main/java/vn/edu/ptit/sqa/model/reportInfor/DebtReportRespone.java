package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
public class DebtReportRespone {
    private DebtReportDTO debtReportDTO;
    private List<DebtCustomerDTO> debtCustomerList;
    private Long totalDebtNumber;
    private PageDto pageDto;
}
