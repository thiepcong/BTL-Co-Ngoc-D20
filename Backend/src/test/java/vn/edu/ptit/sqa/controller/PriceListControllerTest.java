package vn.edu.ptit.sqa.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.PriceListResponse;
import vn.edu.ptit.sqa.service.PriceListService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PriceListControllerTest {

    @Mock
    private PriceListService priceListService;

    @InjectMocks
    private PriceListController priceListController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
void createPriceListWhenCustomerTypeExists() {
    PriceListRequest priceListRequest = new PriceListRequest();
    when(priceListService.createPriceList(1, priceListRequest)).thenReturn(new PriceListResponse());

    ResponseEntity<?> result = priceListController.createPriceList(0, priceListRequest);

    assertEquals(ResponseEntity.ok(new PriceListResponse()), result);
}

@Test
void createPriceListWhenCustomerTypeDoesNotExist() {
    PriceListRequest priceListRequest = new PriceListRequest();
    when(priceListService.createPriceList(999, priceListRequest)).thenReturn(null);

    ResponseEntity<?> result = priceListController.createPriceList(999, priceListRequest);

    assertEquals(ResponseEntity.ok(null), result);
}
    @Test
    void getPriceListByIdWhenIdExists() {
        PriceListResponse priceListResponse = new PriceListResponse();
        when(priceListService.getPriceListResponseById(1)).thenReturn(priceListResponse);

        ResponseEntity<?> result = priceListController.getPriceListById(1);

        assertEquals(ResponseEntity.ok(priceListResponse), result);
    }

    @Test
    void getPriceListByIdWhenIdDoesNotExist() {
        when(priceListService.getPriceListResponseById(999)).thenReturn(null);

        ResponseEntity<?> result = priceListController.getPriceListById(999);

        assertEquals(ResponseEntity.ok(null), result);
    }


}