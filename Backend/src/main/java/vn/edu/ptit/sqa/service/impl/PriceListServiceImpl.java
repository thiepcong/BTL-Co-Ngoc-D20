package vn.edu.ptit.sqa.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.config.AppProperties;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.PriceListResponse;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.UserTypeRepo;
import vn.edu.ptit.sqa.service.PriceListService;
import vn.edu.ptit.sqa.service.PriceScaleService;
import vn.edu.ptit.sqa.service.UserService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceListServiceImpl implements PriceListService {

    private final PriceListRepo priceListRepo;

    private final UserService userService;

    private final PriceScaleService priceScaleService;

    private final UserTypeRepo userTypeRepo;
    @Override
    @Transactional
    public PriceListResponse createPriceList(Integer customerTypeId, PriceListRequest priceListRequest) {
        Optional<UserType> userType = userTypeRepo.findById(customerTypeId);
        if(userType.isEmpty()) throw new NotFoundException("customer type id " + customerTypeId);
        PriceList priceList = ConverterUtil.mappingToObject(priceListRequest, PriceList.class);
        priceList.setUser(userService.getUser());
        priceList.setStatus(AppProperties.PRICE_LIST.STATUS.ACTIVE);
        priceList.setUserType(userType.get());
        priceList.setListPriceScales(null);
        priceListRepo.save(priceList);
        List<PriceScaleResponse> priceScaleRes = priceScaleService.createPriceScale(priceList.getId(), priceListRequest.getListPriceScales());
        PriceListResponse priceListResponse = ConverterUtil.mappingToObject(priceList, PriceListResponse.class);
        priceListResponse.setListPriceScales(priceScaleRes);
        return priceListResponse;
    }

    @Override
    public PriceListResponse getPriceListResponseById(Integer id) {
        PriceList priceList = priceListRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("PriceList id " + id));
        return ConverterUtil.mappingToObject(priceList, PriceListResponse.class);
    }

    @Override
    public DataTableResults<PriceListResponse> getAllPriceList(PaginationRequest paginationRequest){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable paging = PageRequest.of(paginationRequest.getPageNum()-1, paginationRequest.getPageSize(), sort);
        Page<PriceList> priceLists = priceListRepo.findAll(paging);
        List<PriceList> priceList = priceLists.getContent();

        List<PriceListResponse> priceListResponse = ConverterUtil.mapList(priceList, PriceListResponse.class);
        DataTableResults<PriceListResponse> res = new DataTableResults<>(priceListResponse);
        res.setTotalPages(priceLists.getTotalPages());
        res.setTotalItems(priceLists.getTotalElements());
        res.setCurrentPage(priceLists.getNumber()+1);
        return res;
    }
}
