package vn.edu.ptit.sqa.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.service.PriceScaleService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/price_scale")
public class PriceScaleController {
//    @Autowired
//    PriceScaleService priceScaleService;
//
//    @PostMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
//    public ResponseEntity<?> createPriceScale(@PathVariable("id") Integer id,
//                                @Valid @RequestBody List<PriceScaleRequest> priceScaleRequests){
//        return ResponseEntity.ok(priceScaleService.createPriceScale(id, priceScaleRequests));
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
//    public ResponseEntity<?> getPriceScaleById(@PathVariable("id") Integer id){
//        return ResponseEntity.ok(priceScaleService.getPriceScaleById(id));
//    }
}
