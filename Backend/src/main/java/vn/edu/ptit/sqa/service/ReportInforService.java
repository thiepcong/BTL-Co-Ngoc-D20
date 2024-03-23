package vn.edu.ptit.sqa.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportInforRequest;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportInforResponse;

@Service
public interface ReportInforService {
    ReportInforResponse getReportByAddress(ReportInforRequest request, Pageable pageable);
}
