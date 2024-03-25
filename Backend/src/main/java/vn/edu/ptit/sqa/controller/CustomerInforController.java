package vn.edu.ptit.sqa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;
import vn.edu.ptit.sqa.service.CustomerInforService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/customer")
public class CustomerInforController {

    @Autowired
    private CustomerInforService service;

    @GetMapping("/listByAdsress")
    public ResponseEntity<?> getClientListByAddress(@RequestBody ReportInforRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ResponseEntity.ok(service.getReportByAddress(request, pageable));
    }

    @GetMapping("/UnPaidListByAdsress")
    public ResponseEntity<?> getUnPaidClientList(@RequestBody ReportInforRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ResponseEntity.ok(service.getUnPaidClientList(request, pageable));
    }

    @GetMapping("/getPaidCustomers")
    public ResponseEntity<?> getNewCustomers(@RequestBody ReportInforRequest request){
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ResponseEntity.ok(service.getPaidCustomer(request, pageable));
    }
}
