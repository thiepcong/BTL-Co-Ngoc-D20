package vn.edu.ptit.sqa.service;

import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.model.PriceScaleResponse;

import java.util.List;

public interface PriceScaleService {
    List<PriceScaleResponse> createPriceScale(Integer PriceListId, List<PriceScaleRequest> priceScaleRequest);
    PriceScaleResponse getPriceScaleById(Integer id);
}
