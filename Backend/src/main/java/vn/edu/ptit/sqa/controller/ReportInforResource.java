package vn.edu.ptit.sqa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportInforRequest;
import vn.edu.ptit.sqa.service.ReportInforService;

@RestController
public class ReportInforResource {

    @Autowired
    private ReportInforService service;

    @GetMapping("/api/waterMeasurementReport/list")
    public ResponseEntity<?> getClientListByAddress(@RequestBody ReportInforRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ResponseEntity.ok(service.getReportByAddress(request, pageable));
    }

}
