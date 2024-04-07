package vn.edu.ptit.sqa.service.impl;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import vn.edu.ptit.sqa.exception.NotFoundException;
import org.mockito.MockitoAnnotations;
import vn.edu.ptit.sqa.model.PriceScaleResponse;

import java.util.Optional;
import java.util.ArrayList;

import vn.edu.ptit.sqa.repository.PriceScaleRepo;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.repository.PriceListRepo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class PriceScaleServiceImplSapientGeneratedTest {

    private final PriceListRepo priceListRepoMock = mock(PriceListRepo.class, "priceListRepo");

    private final PriceScaleRepo priceScaleRepoMock = mock(PriceScaleRepo.class, "priceScaleRepo");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private PriceScaleServiceImpl target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${46e5877e-a293-32b5-8733-7fe6d72a3e5d}, hash: 8577FE659699AA35B2042D27B0965F13
    @Disabled()
    @Test()
    void createPriceScaleThrowsNotFoundException() {
        //Arrange Statement(s)
        target = new PriceScaleServiceImpl();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        doReturn(Optional.empty()).when(priceListRepoMock).findById(2);
        List<PriceScaleRequest> priceScaleRequestList = new ArrayList<>();
        NotFoundException notFoundException = new NotFoundException("price list id 2");
        //Act Statement(s)
        final NotFoundException result = assertThrows(NotFoundException.class, () -> {
            target.createPriceScale(2, priceScaleRequestList);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(notFoundException.getMessage()));
            verify(priceListRepoMock).findById(2);
        });
    }
}
