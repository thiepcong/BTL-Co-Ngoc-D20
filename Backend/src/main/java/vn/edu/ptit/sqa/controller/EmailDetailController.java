package vn.edu.ptit.sqa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.sqa.model.AllUserFilter;
import vn.edu.ptit.sqa.model.EmailDetailAM;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.service.EmailDetailService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emailDetail")
public class EmailDetailController {

    private final EmailDetailService emailDetailService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PostMapping("/send")
    public ResponseEntity<?> createEmailDetail(@RequestBody EmailDetailAM emailDetailAM,
                                               @RequestParam(name = "filterAll", required = false, defaultValue = "False") Boolean isFilterAll,
                                               @RequestParam(name = "city", required = false, defaultValue = "") String city,
                                               @RequestParam(name = "district", required = false, defaultValue = "") String district,
                                               @RequestParam(name = "ward", required = false, defaultValue = "") String ward,
                                               @RequestParam(name = "village", required = false, defaultValue = "") String village,
                                               @RequestParam(name = "customerName", required = false, defaultValue = "") String customerName,
                                               @RequestParam(name = "isPaid", required = false, defaultValue = "False") Boolean isPaid){
        AllUserFilter allUserFilter = new AllUserFilter(isFilterAll, city, district, ward, village, customerName, isPaid);
        emailDetailService.createEmailDetail(emailDetailAM, allUserFilter);
        return ResponseEntity.ok("OK");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmailDetailById(@PathVariable("id") Long id){
        return ResponseEntity.ok(emailDetailService.getEmailDetailById(id));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping
    public ResponseEntity<?> getAllEmailDetails(
                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize){
        PaginationRequest paginationRequest = new PaginationRequest(true, pageNum, pageSize);
        return ResponseEntity.ok(emailDetailService.getAllEmailDetails(paginationRequest));
    }
}
