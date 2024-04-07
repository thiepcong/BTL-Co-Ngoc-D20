package vn.edu.ptit.sqa.service.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.PriceListResponse;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.UserTypeRepo;
import vn.edu.ptit.sqa.service.PriceScaleService;
import vn.edu.ptit.sqa.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class PriceListServiceImplCopilot {
    @Mock
    UserTypeRepo userTypeRepo;

    @Mock
    PriceListRepo priceListRepo;

    @InjectMocks
    UserServiceImpl userService;

    @InjectMocks
    PriceScaleServiceImpl priceScaleService;

    @InjectMocks
    PriceListServiceImpl priceListServiceImpl;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
void createPriceListWithValidCustomerTypeIdAndPriceListRequest() {
    Integer customerTypeId = 1;
    PriceListRequest priceListRequest = new PriceListRequest();
    when(userTypeRepo.findById(customerTypeId)).thenReturn(Optional.of(new UserType()));
    when(userService.getUser()).thenReturn(new User());
    when(priceScaleService.createPriceScale(anyInt(), anyList())).thenReturn(new ArrayList<>());
    when(priceListRepo.save(any(PriceList.class))).thenReturn(new PriceList());

    PriceListResponse result = priceListServiceImpl.createPriceList(customerTypeId, priceListRequest);

    assertNotNull(result);
}

@Test
void createPriceListWithInvalidCustomerTypeId() {
    Integer customerTypeId = 999;
    PriceListRequest priceListRequest = new PriceListRequest();
    when(userTypeRepo.findById(customerTypeId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> priceListServiceImpl.createPriceList(customerTypeId, priceListRequest));
}

@Test
void createPriceListWhenPriceScaleCreationFails() {
    Integer customerTypeId = 1;
    PriceListRequest priceListRequest = new PriceListRequest();
    when(userTypeRepo.findById(customerTypeId)).thenReturn(Optional.of(new UserType()));
    when(userService.getUser()).thenReturn(new User());
    when(priceScaleService.createPriceScale(anyInt(), anyList())).thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> priceListServiceImpl.createPriceList(customerTypeId, priceListRequest));
}
}
