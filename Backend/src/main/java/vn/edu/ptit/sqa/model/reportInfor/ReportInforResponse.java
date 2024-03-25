package vn.edu.ptit.sqa.model.reportInfor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.ptit.sqa.model.customer.CustomerDTO;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportInforResponse {
    private CustomerDTO customerDTO;
    private ReportDTO reportDTO;
    private List<ReportDTO> reportDTOList;
    private DebtReportDTO debtReportDTO;
    private PageDto pageDto;
}
