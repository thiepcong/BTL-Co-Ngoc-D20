package vn.edu.ptit.sqa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.ExistedException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.reportInfor.ReportDTO;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforResponse;
import vn.edu.ptit.sqa.repository.CustomerRepository;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.util.DateUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerInforServiceImplTest {
    @InjectMocks
    private CustomerInforServiceImpl customerInforService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PriceListRepo priceListRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Pageable setPage(int a, int b) {
        Pageable pageable = PageRequest.of(a - 1, b);
        return pageable;
    }

    private List<PriceScale> setPriceScales() {
        List<PriceScale> priceScales = new ArrayList<>();

        priceScales.add(new PriceScale(0, 50, 2100.0F));
        priceScales.add(new PriceScale(50, 100, 2300.0F));
        priceScales.add(new PriceScale(100, 150, 2500.0F));
        priceScales.add(new PriceScale(150, 200, 2700.0F));
        priceScales.add(new PriceScale(200, 300, 2900.0F));
        priceScales.add(new PriceScale(300, 10000000, 3200.0F));
        return priceScales;
    }

    private ReportInforRequest mockReportInforRequest() {
        return new ReportInforRequest().setProvine("Ha Noi").setDistrict("Ha Dong").setWard("Van Quan").setMonth(new Date()).setPage(1).setSize(1).setSearch("Ha Dong");
    }

    //getAll service
    @Test
    public void testGetReportByAddress_NormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();

        // Mock data
        List<ReportDTO> reportDTOList = new ArrayList<>();
        reportDTOList.add(new ReportDTO().setProvine("Ha Noi").setDistrict("Ha Dong").setWard("Van Quan"));

        Page<ReportDTO> mockReportDtoPage = new PageImpl<>(reportDTOList);

        when(customerRepository.findByAddressPage(request.getProvine(), request.getDistrict(), request.getWard(), request.getSearch(), start, end, pageable)).thenReturn(mockReportDtoPage);

        ReportInforResponse response = customerInforService.getReportByAddress(request, pageable);

        // Assertions
        assertNotNull(response);
        assertEquals(reportDTOList.size(), response.getReportDTOList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
    }

    @Test
    public void testGetReportByAddress_NullData() {
        ReportInforRequest request = mockReportInforRequest();

        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Pageable pageable = Pageable.unpaged();

        when(customerRepository.findByAddressPage(request.getProvine(), request.getDistrict(), request.getWard(), request.getSearch(), start, end, pageable)).thenReturn(null);

        // Assertions
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getReportByAddress(request, pageable);
        });
    }

    //getUnPaid service
    @Test
    public void testGetUnPaidClientListWithNormalData() {
        //to do
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();

        // Mock data
        List<ReportDTO> reportDTOList = new ArrayList<>();
        reportDTOList.add(new ReportDTO().setProvine("Ha Noi").setDistrict("Ha Dong").setWard("Van Quan"));

        Page<ReportDTO> mockReportDtoPage = new PageImpl<>(reportDTOList);

        when(customerRepository.findByAddressPage(request.getProvine(), request.getDistrict(), request.getWard(), request.getSearch(), start, end, pageable)).thenReturn(mockReportDtoPage);

        ReportInforResponse response = customerInforService.getReportByAddress(request, pageable);

        // Assertions
        assertNotNull(response);
        assertEquals(reportDTOList.size(), response.getReportDTOList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
    }

    @Test
    public void testGetUnPaidClientList_NullReportDTOPage_ThrowsNotFoundException() {
        ReportInforRequest request = mockReportInforRequest();

        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Pageable pageable = mock(Pageable.class);
        when(customerRepository.findUnPaidCustomerPageByAddressAndTime(request.getProvine(), request.getDistrict(), request.getWard(), start, end, request.getSearch(), pageable)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getUnPaidClientList(request, pageable);
        });
    }

    @Test
    public void testSetMoneyForListWithEmptyCustomerThrowsNotFoundException() {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCustomerId(1L);
        List<ReportDTO> reportDTOS = Collections.singletonList(reportDTO);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerInforService.setMoneyForList(reportDTOS);
        });
    }

    //    @Test
//    public void testSetMoneyForListWithEmptyOptionalPriceListThrowExistedException() {
//        // Arrange
//        ReportDTO reportDTO = new ReportDTO();
//        reportDTO.setCustomerId(1L);
//        UserType userType = new UserType();
//        userType.setId(1);
//        Customer customer = new Customer().setId(1L).setUserType(userType);
//        List<ReportDTO> reportDTOS = Collections.singletonList(reportDTO);
//        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
//        when(priceListRepo.findByUserType(customer.getUserType().getId(), any(Date.class))).thenReturn(Collections.emptyList());
//
//        // Act & Assert
//        assertThrows(ExistedException.class, () -> {
//            customerInforService.setMoneyForList(reportDTOS);
//        });
//    }
//    @Test
//    public void testSetMoneyForListWithEmptyPriceListThrowExistedException() {
//        // Arrange
//        ReportDTO reportDTO = new ReportDTO();
//        reportDTO.setCustomerId(1L);
//        UserType userType = new UserType();
//        userType.setId(1);
//        Customer customer = new Customer().setId(1L).setUserType(userType);
//        List<ReportDTO> reportDTOS = Collections.singletonList(reportDTO);
//
//        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
//        when(priceListRepo.findByUserType(userType.getId(), any(Date.class))).thenThrow(new ExistedException("PriceList is not found, can't solve money"));
//
//        // Act & Assert
//        assertThrows(ExistedException.class, () -> {
//            customerInforService.setMoneyForList(reportDTOS);
//        });
//    }


}
