package vn.edu.ptit.sqa.service.impl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.edu.ptit.sqa.config.AppProperties;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.PriceListResponse;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.UserTypeRepo;
import vn.edu.ptit.sqa.service.PriceScaleService;
import vn.edu.ptit.sqa.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Nested
@ContextConfiguration(classes = {PriceListServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class PriceListServiceImplTest {

    @Autowired
    private PriceListServiceImpl priceListService;

    @MockBean
    private PriceListRepo priceListRepo;

    @MockBean
    private UserService userService;

    @MockBean
    private PriceScaleService priceScaleService;

    @MockBean
    private UserTypeRepo userTypeRepo;


    @Test
    public void testCreatePriceList_Valid() {
        UserType userType = new UserType();
        when(userTypeRepo.findById(any(Integer.class))).thenReturn(Optional.of(userType));

        List<PriceScaleResponse> priceScaleRes = new ArrayList<>();
        when(priceScaleService.createPriceScale(any(Integer.class), anyList())).thenReturn(priceScaleRes);

        User user = new User();
        when(userService.getUser()).thenReturn(user);

        PriceListResponse result = priceListService.createPriceList(1, new PriceListRequest());

        assertNotNull(result);
        assertEquals(userType.getId(), result.getUserType().getId());
        assertEquals(AppProperties.PRICE_LIST.STATUS.ACTIVE, result.getStatus());
    }

    /**
     * Method under test:
     * {@link PriceListServiceImpl#createPriceList(Integer, PriceListRequest)}
     */
    @Test
    public void createPriceList_UserTypeNotFound() {
        PriceListRequest priceListRequest = new PriceListRequest();
        when(userTypeRepo.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> priceListService.createPriceList(1, priceListRequest));
    }

    @Test
    public void getPriceListResponseById_validPriceListId() {
        PriceList priceList = new PriceList();
        priceList.setId(1);
        when(priceListRepo.findById(any(Integer.class))).thenReturn(Optional.of(priceList));

        PriceListResponse result = priceListService.getPriceListResponseById(1);

        assertEquals(1, result.getId());
    }

    @Test
    public void getPriceListResponseById_NotFound() {
        when(priceListRepo.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> priceListService.getPriceListResponseById(1));
    }

    @Test
    public void getAllPriceList_Valid() {
        PaginationRequest paginationRequest = new PaginationRequest();
        paginationRequest.setPageNum(1);
        paginationRequest.setPageSize(1);

        PriceList priceList = new PriceList();
        priceList.setId(1);

        PriceList priceList2 = new PriceList();
        priceList2.setId(2);

        List<PriceList> priceLists = new ArrayList<>();
        priceLists.add(priceList);
        priceLists.add(priceList2);

        Page<PriceList> page = new PageImpl<>(priceLists, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")), 2);
        when(priceListRepo.findAll(any(Pageable.class))).thenReturn(page );

        DataTableResults<PriceListResponse> result = priceListService.getAllPriceList(paginationRequest);

        assertEquals(2, result.getTotalItems());
        assertEquals(1, result.getCurrentPage());
        assertEquals(result.getData().get(0).getId(), priceLists.get(0).getId());
        verify(priceListRepo).findAll(any(Pageable.class));
    }

    @Test
    public void getAllPriceList_Empty() {
        PaginationRequest paginationRequest = new PaginationRequest();
        paginationRequest.setPageNum(1);
        paginationRequest.setPageSize(1);

        Page<PriceList> page = Page.empty();
        when(priceListRepo.findAll(any(Pageable.class))).thenReturn(page);

        DataTableResults<PriceListResponse> result = priceListService.getAllPriceList(paginationRequest);

        assertEquals(0, result.getTotalItems());
        assertEquals(1, result.getCurrentPage());
    }
}