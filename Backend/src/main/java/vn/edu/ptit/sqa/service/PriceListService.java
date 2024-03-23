package vn.edu.ptit.sqa.service;

import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.PriceListResponse;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;

public interface PriceListService {

    PriceListResponse createPriceList(Integer customerTypeId, PriceListRequest priceListRequest);

    PriceListResponse getPriceListResponseById(Integer id);

    DataTableResults<PriceListResponse> getAllPriceList(PaginationRequest paginationRequest);

}
