package vn.edu.ptit.sqa.service.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.PriceListResponse;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.UserTypeRepo;
import vn.edu.ptit.sqa.service.PriceScaleService;
import vn.edu.ptit.sqa.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PriceListServiceImplTest {

    @InjectMocks
    private PriceListServiceImpl priceListService;

    @Mock
    private PriceListRepo priceListRepo;

    @Mock
    private UserService userService;

    @Mock
    private PriceScaleService priceScaleService;

    @Mock
    private UserTypeRepo userTypeRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPriceList_HappyPath() {
        PriceListRequest priceListRequest = new PriceListRequest();
        UserType userType = new UserType();
        userType.setId(1);
        when(userTypeRepo.findById(any(Integer.class))).thenReturn(Optional.of(userType));

        PriceListResponse result = priceListService.createPriceList(1, priceListRequest);

        assertEquals(1, result.getUserType().getId());
    }

    @Test
    public void createPriceList_UserTypeNotFound() {
        PriceListRequest priceListRequest = new PriceListRequest();
        when(userTypeRepo.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> priceListService.createPriceList(1, priceListRequest));
    }

    @Test
    public void getPriceListResponseById_HappyPath() {
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
    public void getAllPriceList_HappyPath() {
        PaginationRequest paginationRequest = new PaginationRequest();
        paginationRequest.setPageNum(1);
        paginationRequest.setPageSize(1);

        DataTableResults<PriceListResponse> result = priceListService.getAllPriceList(paginationRequest);

        assertEquals(1, result.getTotalItems());
        assertEquals(1, result.getCurrentPage());
    }
}