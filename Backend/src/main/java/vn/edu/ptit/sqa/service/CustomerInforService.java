package vn.edu.ptit.sqa.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.model.reportInfor.*;

@Service
public interface CustomerInforService {
    ReportInforResponse getReportByAddress(ReportInforRequest request, Pageable pageable);

    ReportInforResponse getUnPaidClientList(ReportInforRequest request, Pageable pageable);

    DebtReportDTO getDebtCustomerNumber(ReportInforRequest request);

    DebtReportResponse getDebtCustomerList(ReportInforRequest request, Pageable pageable);

    NewCustomerResponse getNewCustomerList(ReportInforRequest  request, Pageable pageable);

    NewCustomerReportDTO getNewCustomerNumber(ReportInforRequest  request);
    ReportInforResponse getPaidCustomer(ReportInforRequest  request, Pageable pageable);

    RevenueResponse getRevenue(ReportInforRequest  request, Pageable pageable);
}
