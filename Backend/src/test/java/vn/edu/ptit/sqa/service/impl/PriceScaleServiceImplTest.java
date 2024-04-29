package vn.edu.ptit.sqa.service.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.PriceScaleRepo;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PriceScaleServiceImplTest {

    @InjectMocks
    private PriceScaleServiceImpl priceScaleService;

    @Mock
    private PriceListRepo priceListRepo;

    @Mock
    private PriceScaleRepo priceScaleRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPriceScale_HappyPath() {
        PriceList priceList = new PriceList();
        priceList.setId(1);
        when(priceListRepo.findById(any(Integer.class))).thenReturn(Optional.of(priceList));

        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest();
        priceScaleRequest1.setStartIndex(1);
        priceScaleRequest1.setEndIndex(2);

        PriceScaleRequest priceScaleRequest2 = new PriceScaleRequest();
        priceScaleRequest2.setStartIndex(3);
        priceScaleRequest2.setEndIndex(4);

        priceScaleService.createPriceScale(1, Arrays.asList(priceScaleRequest1, priceScaleRequest2));
    }

    @Test
    public void createPriceScale_PriceListNotFound() {
        when(priceListRepo.findById(any(Integer.class))).thenReturn(Optional.empty());

        PriceScaleRequest priceScaleRequest = new PriceScaleRequest();
        priceScaleRequest.setStartIndex(1);
        priceScaleRequest.setEndIndex(2);

        assertThrows(NotFoundException.class, () -> priceScaleService.createPriceScale(1, Arrays.asList(priceScaleRequest)));
    }

    @Test
    public void createPriceScale_BadRequest() {
        PriceList priceList = new PriceList();
        priceList.setId(1);
        when(priceListRepo.findById(any(Integer.class))).thenReturn(Optional.of(priceList));

        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest();
        priceScaleRequest1.setStartIndex(3);
        priceScaleRequest1.setEndIndex(2);

        assertThrows(BadRequestException.class, () -> priceScaleService.createPriceScale(1, Arrays.asList(priceScaleRequest1)));
    }

    @Test
    public void getPriceScaleById_HappyPath() {
        PriceScale priceScale = new PriceScale();
        priceScale.setId(1);
        when(priceScaleRepo.findById(any(Integer.class))).thenReturn(Optional.of(priceScale));

        assertEquals(1, priceScaleService.getPriceScaleById(1).getId());
    }

    @Test
    public void getPriceScaleById_NotFound() {
        when(priceScaleRepo.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> priceScaleService.getPriceScaleById(1));
    }
}