package vn.edu.ptit.sqa.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.model.reportInfor.DebtReportDTO;
import vn.edu.ptit.sqa.model.reportInfor.DebtReportRespone;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforResponse;

@Service
public interface CustomerInforService {
    ReportInforResponse getReportByAddress(ReportInforRequest request, Pageable pageable);

    ReportInforResponse getUnPaidClientList(ReportInforRequest request, Pageable pageable);

    DebtReportDTO getDebtCustomerNumber(ReportInforRequest request);

    DebtReportRespone getDebtCustomerList(ReportInforRequest request, Pageable pageable);

    ReportInforResponse getNewCustomer(ReportInforRequest  request, Pageable pageable);
    ReportInforResponse getPaidCustomer(ReportInforRequest  request, Pageable pageable);
}
