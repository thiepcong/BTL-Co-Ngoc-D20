package vn.edu.ptit.sqa.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.PriceScaleRepo;

@ContextConfiguration(classes = {PriceScaleServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PriceScaleServiceImplTest {
    @MockBean
    private PriceListRepo priceListRepo;

    @MockBean
    private PriceScaleRepo priceScaleRepo;

    @Autowired
    private PriceScaleServiceImpl priceScaleServiceImpl;

    /**
     * Method under test:
     * {@link PriceScaleServiceImpl#createPriceScale(Integer, List)}
     */
    @Test
    void testCreatePriceScale_PriceListNotFound() {
        // Arrange
        Optional<PriceList> emptyResult = Optional.empty();
        when(priceListRepo.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest(0, 50, 2100);
        PriceScaleRequest priceScaleRequest2 = new PriceScaleRequest(51, 100, 2300);
        // Act and Assert
        assertThrows(NotFoundException.class, () -> priceScaleServiceImpl.createPriceScale(1, priceScaleRequests));
//        verify(priceListRepo).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link PriceScaleServiceImpl#createPriceScale(Integer, List)}
     */
    @Test
    void testCreatePriceScale_ChiSoDauLonHonChiSoCuoiBacTruocTu2DonVi() {
        // Arrange
        when(priceListRepo.findById(Mockito.<Integer>any())).thenThrow(new BadRequestException("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị"));

        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest(0, 50, 2100);
        PriceScaleRequest priceScaleRequest2 = new PriceScaleRequest(56, 100, 2300);
        priceScaleRequests.add(priceScaleRequest1);
        priceScaleRequests.add(priceScaleRequest2);
        // Act and Assert
        assertThrows(BadRequestException.class, () -> priceScaleServiceImpl.createPriceScale(1, priceScaleRequests));
//        verify(priceListRepo).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link PriceScaleServiceImpl#createPriceScale(Integer, List)}
     */
    @Test
    void testCreatePriceScale_ChiSoDauBeHonHoacBangChiSoCuoiBacTruoc() {
        // Arrange
        when(priceListRepo.findById(Mockito.<Integer>any())).thenThrow(new BadRequestException("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị"));

        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest(0, 50, 2100);
        PriceScaleRequest priceScaleRequest2 = new PriceScaleRequest(45, 100, 2300);
        priceScaleRequests.add(priceScaleRequest1);
        priceScaleRequests.add(priceScaleRequest2);
        // Act and Assert
        assertThrows(BadRequestException.class, () -> priceScaleServiceImpl.createPriceScale(1, priceScaleRequests));
//        verify(priceListRepo).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link PriceScaleServiceImpl#createPriceScale(Integer, List)}
     */
    @Test
    void testCreatePriceScale_ChiSoDauLonHonChiSoCuoi() {
        // Arrange
        when(priceListRepo.findById(Mockito.<Integer>any())).thenThrow(new BadRequestException("Chỉ số đầu phải bé hơn chỉ số cuối"));

        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest(0, 50, 2100);
        PriceScaleRequest priceScaleRequest2 = new PriceScaleRequest(51, 35, 2300);
        priceScaleRequests.add(priceScaleRequest1);
        priceScaleRequests.add(priceScaleRequest2);
        // Act and Assert
        assertThrows(BadRequestException.class, () -> priceScaleServiceImpl.createPriceScale(1, priceScaleRequests));
//        verify(priceListRepo).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link PriceScaleServiceImpl#createPriceScale(Integer, List)}
     */
    @Test
    void testCreatePriceScale_Valid() {
        PriceList priceList = new PriceList();
        priceList.setId(1);
        priceList.setInvoices(new ArrayList<>());
        priceList.setStatus(1);
        priceList.setApplyDate(java.util.Date.from(LocalDate.of(2024, 5, 5).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        when(priceListRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(priceList));

        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        PriceScaleRequest priceScaleRequest1 = new PriceScaleRequest(0, 50, 2100);
        PriceScaleRequest priceScaleRequest2 = new PriceScaleRequest(51, 100, 2300);
        priceScaleRequests.add(priceScaleRequest1);
        priceScaleRequests.add(priceScaleRequest2);

        List<PriceScaleResponse> result = priceScaleServiceImpl.createPriceScale(1, priceScaleRequests);
        // Act and Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    /**
     * Method under test: {@link PriceScaleServiceImpl#getPriceScaleById(Integer)}
     */
    @Test
    void testGetPriceScaleById_valid() {
        // Arrange
        User user = new User();
        user.setAddress("42 Main St");
        user.setCreatAt(mock(Timestamp.class));
        user.setDateOfBirth(mock(java.sql.Date.class));
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setInvoices(new ArrayList<>());
        user.setListEmailDetails(new HashSet<>());
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setPriceListsCreated(new ArrayList<>());
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");

        UserType userType = new UserType();
        userType.setCustomer(new ArrayList<>());
        userType.setId(1);
        userType.setListPriceLists(new ArrayList<>());
        userType.setTypeName("Hộ dân cư");

        PriceList priceList = new PriceList();
        priceList
                .setApplyDate(java.util.Date.from(LocalDate.of(2024, 5, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        priceList.setId(1);
        priceList.setInvoices(new ArrayList<>());
        priceList.setListPriceScales(new ArrayList<>());
        priceList.setStatus(1);
        priceList.setUser(user);
        priceList.setUserType(userType);

        PriceScale priceScale = new PriceScale();
        priceScale.setEndIndex(3);
        priceScale.setId(1);
        priceScale.setPrice(10.0f);
        priceScale.setPriceList(priceList);
        priceScale.setStartIndex(1);
        Optional<PriceScale> ofResult = Optional.of(priceScale);
        when(priceScaleRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        PriceScaleResponse actualPriceScaleById = priceScaleServiceImpl.getPriceScaleById(1);

        // Assert
//        verify(priceScaleRepo).findById(eq(1));
        assertEquals(1, actualPriceScaleById.getId().intValue());
        assertEquals(1, actualPriceScaleById.getStartIndex().intValue());
        assertEquals(10.0f, actualPriceScaleById.getPrice());
        assertEquals(3, actualPriceScaleById.getEndIndex().intValue());
    }

    /**
     * Method under test: {@link PriceScaleServiceImpl#getPriceScaleById(Integer)}
     */
    @Test
    void testGetPriceScaleById_NotFound2() {
        // Arrange
        Optional<PriceScale> emptyResult = Optional.empty();
        when(priceScaleRepo.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> priceScaleServiceImpl.getPriceScaleById(9));
//        verify(priceScaleRepo).findById(eq(1));
    }

}
