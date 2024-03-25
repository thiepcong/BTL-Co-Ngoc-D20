package vn.edu.ptit.sqa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ptit.sqa.model.EmailAttachmentResponse;
import vn.edu.ptit.sqa.service.EmailAttachmentService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/emailAttachment")
public class EmailAttachmentController {
    @Autowired
    EmailAttachmentService emailAttachmentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public ResponseEntity<List<EmailAttachmentResponse>> addAttachment(@RequestParam("attachments") List<MultipartFile> files){

        return new ResponseEntity<>(emailAttachmentService.uploadAllAttachments(files), HttpStatus.OK);
    };
}
