package vn.edu.ptit.sqa.model.waterMeasurementReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.ptit.sqa.model.customer.CustomerDTO;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportInforResponse {
    private CustomerDTO customerDTO;
    private ReportDTO reportDTO;
    private List<ReportDTO> reportDTOList;
    private PageDto pageDto;
}
