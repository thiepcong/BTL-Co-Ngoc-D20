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
@RequestMapping("/api/report")
public class ReportInforController {

    @Autowired
    private CustomerInforService customerInforService;

    @GetMapping("/getDebtCustomer")
    public ResponseEntity<?> getDebtCustomer(@RequestBody ReportInforRequest request){
        return ResponseEntity.ok(customerInforService.getDebtCustomerNumber(request));
    }

    @GetMapping("/getDebtCustomerList")
    public ResponseEntity<?> getDebtCustomerList(@RequestBody ReportInforRequest request){
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ResponseEntity.ok(customerInforService.getDebtCustomerList(request, pageable));
    }

    @GetMapping("/getNewCustomers")
    public ResponseEntity<?> getNewCustomers(@RequestBody ReportInforRequest request){
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ResponseEntity.ok(customerInforService.getNewCustomer(request, pageable));
    }
}
