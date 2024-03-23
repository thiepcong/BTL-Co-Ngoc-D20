package vn.edu.ptit.sqa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.service.PriceListService;

@RestController
@RequestMapping("/api/price_list")
public class PriceListController {
    @Autowired
    PriceListService priceListService;

    @PostMapping("{customerTypeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public ResponseEntity<?> createPriceList(@PathVariable("customerTypeId") Integer customerTypeId,
                                @RequestBody PriceListRequest priceListRequest){
        return ResponseEntity.ok(priceListService.createPriceList(customerTypeId, priceListRequest));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public ResponseEntity<?> getPriceListById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(priceListService.getPriceListResponseById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public ResponseEntity<?> getAllPriceList(
                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize){
        PaginationRequest paginationRequest = new PaginationRequest(true, pageNum, pageSize);
        return ResponseEntity.ok(priceListService.getAllPriceList(paginationRequest));
    }

}
