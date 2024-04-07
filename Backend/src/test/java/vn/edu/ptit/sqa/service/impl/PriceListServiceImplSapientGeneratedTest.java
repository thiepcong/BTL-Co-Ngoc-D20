package vn.edu.ptit.sqa.service.impl;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import vn.edu.ptit.sqa.model.PriceListRequest;
import vn.edu.ptit.sqa.model.UserTypeResponse;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceListResponse;

import java.util.Date;

import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import org.mockito.MockedStatic;
import vn.edu.ptit.sqa.service.PriceScaleService;
import vn.edu.ptit.sqa.repository.UserTypeRepo;
import vn.edu.ptit.sqa.model.PriceScaleRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class PriceListServiceImplSapientGeneratedTest {

    private final UserTypeRepo userTypeRepoMock = mock(UserTypeRepo.class, "userTypeRepo");

    private final UserService userServiceMock = mock(UserService.class, "userService");

    private final PriceListRepo priceListRepoMock = mock(PriceListRepo.class, "priceListRepo");

    private final PriceScaleService priceScaleServiceMock = mock(PriceScaleService.class, "priceScaleService");

    //Sapient generated method id: ${556f2b07-f57d-3373-a04d-692dc3945587}, hash: 9C9418435C47C6425922DDB84D58A993
    @Disabled()
    @Test()
    void createPriceListWhenUserTypeIsEmptyThrowsNotFoundException() {
        /* Branches:* (userType.isEmpty()) : true*/
        //Arrange Statement(s)
        PriceListServiceImpl target = new PriceListServiceImpl(priceListRepoMock, userServiceMock, priceScaleServiceMock, userTypeRepoMock);
        doReturn(Optional.empty()).when(userTypeRepoMock).findById(2);
        PriceListRequest priceListRequestMock = mock(PriceListRequest.class);
        NotFoundException notFoundException = new NotFoundException("customer type id 2");
        //Act Statement(s)
        final NotFoundException result = assertThrows(NotFoundException.class, () -> {
            target.createPriceList(2, priceListRequestMock);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(notFoundException.getMessage()));
            verify(userTypeRepoMock).findById(2);
        });
    }

    //Sapient generated method id: ${132f324d-9ce1-3bf5-b03a-2dca3094aa8c}, hash: 6A40B5431B911D2FC4F1C38AD68EC9B6
    @Disabled()
    @Test()
    void createPriceListWhenUserTypeNotIsEmpty() {
        /* Branches:* (userType.isEmpty()) : false*/
        //Arrange Statement(s)
        User userMock = mock(User.class);
        UserType userTypeMock = mock(UserType.class);
        UserTypeResponse userTypeResponseMock = mock(UserTypeResponse.class);
        try (MockedStatic<ConverterUtil> converterUtil = mockStatic(ConverterUtil.class)) {
            Date date = new Date();
            List list = new ArrayList<>();
            PriceList priceList = new PriceList(0, date, 1, list, userMock, null, userTypeMock);
            Date date2 = new Date();
            List<PriceScaleRequest> priceScaleRequestList = new ArrayList<>();
            PriceListRequest priceListRequest = new PriceListRequest(date2, priceScaleRequestList);
            converterUtil.when(() -> ConverterUtil.mappingToObject(priceListRequest, PriceList.class)).thenReturn(priceList);
            Date date3 = new Date();
            List<PriceScaleResponse> priceScaleResponseList = new ArrayList<>();
            PriceListResponse priceListResponse = new PriceListResponse(0, userTypeResponseMock, date3, 0, priceScaleResponseList);
            converterUtil.when(() -> ConverterUtil.mappingToObject(priceList, PriceListResponse.class)).thenReturn(priceListResponse);
            PriceListServiceImpl target = new PriceListServiceImpl(priceListRepoMock, userServiceMock, priceScaleServiceMock, userTypeRepoMock);
            doReturn(Optional.of(userTypeMock)).when(userTypeRepoMock).findById(0);
            doReturn(userMock).when(userServiceMock).getUser();
            Object object = new Object();
            doReturn(object).when(priceListRepoMock).save(priceList);
            doReturn(priceScaleResponseList).when(priceScaleServiceMock).createPriceScale(0, priceScaleRequestList);
            //Act Statement(s)
            PriceListResponse result = target.createPriceList(0, priceListRequest);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, equalTo(priceListResponse));
                converterUtil.verify(() -> ConverterUtil.mappingToObject(priceListRequest, PriceList.class), atLeast(1));
                converterUtil.verify(() -> ConverterUtil.mappingToObject(priceList, PriceListResponse.class), atLeast(1));
                verify(userTypeRepoMock).findById(0);
                verify(userServiceMock).getUser();
                verify(priceListRepoMock).save(priceList);
                verify(priceScaleServiceMock).createPriceScale(0, priceScaleRequestList);
            });
        }
    }
}
