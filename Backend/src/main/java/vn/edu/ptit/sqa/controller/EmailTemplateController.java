package vn.edu.ptit.sqa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.sqa.model.TemplateAM;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.service.EmailTemplateService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emailTemplate")
public class EmailTemplateController {
    private final EmailTemplateService emailTemplateService;

//    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
//    public ResponseEntity<?> createTemplate(@Valid @RequestBody TemplateAM templateAM){
//        return ResponseEntity.ok(emailTemplateService.createTemplate(templateAM));
//    }
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
//    public ResponseEntity<?> getEmailTemplateById(@PathVariable("id") Integer id){
//        return ResponseEntity.ok(emailTemplateService.getEmailTemplateResById(id));
//    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public ResponseEntity<?> getAllEmailTemplates(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize){
        PaginationRequest paginationRequest = new PaginationRequest(true, pageNum, pageSize);
        return ResponseEntity.ok(emailTemplateService.getAllEmailTemplates(paginationRequest));
    }
}
