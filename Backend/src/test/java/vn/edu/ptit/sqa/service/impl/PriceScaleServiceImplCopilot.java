package vn.edu.ptit.sqa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.PriceScaleRepo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class PriceScaleServiceImplCopilot {
    @Mock
    PriceListRepo priceListRepo;
    @Mock
    PriceScaleRepo priceScaleRepo;

    @InjectMocks
    PriceScaleServiceImpl target;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
void createPriceScaleWithValidPriceListIdAndValidPriceScaleRequests() {
    Integer priceListId = 1;
    List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
    PriceScaleRequest priceScaleRequest = new PriceScaleRequest();
    priceScaleRequest.setStartIndex(1);
    priceScaleRequest.setEndIndex(2);
    priceScaleRequests.add(priceScaleRequest);

    when(priceListRepo.findById(priceListId)).thenReturn(Optional.of(new PriceList()));
    when(priceScaleRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

    List<PriceScaleResponse> result = target.createPriceScale(priceListId, priceScaleRequests);

    assertNotNull(result);
}

@Test
void createPriceScaleWithInvalidPriceListId() {
    Integer priceListId = 999;
    List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();

    when(priceListRepo.findById(priceListId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> target.createPriceScale(priceListId, priceScaleRequests));
}

@Test
void createPriceScaleWithInvalidPriceScaleRequests() {
    Integer priceListId = 1;
    List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
    PriceScaleRequest priceScaleRequest = new PriceScaleRequest();
    priceScaleRequest.setStartIndex(2);
    priceScaleRequest.setEndIndex(1);
    priceScaleRequests.add(priceScaleRequest);

    when(priceListRepo.findById(priceListId)).thenReturn(Optional.of(new PriceList()));

    assertThrows(BadRequestException.class, () -> target.createPriceScale(priceListId, priceScaleRequests));
}
}
