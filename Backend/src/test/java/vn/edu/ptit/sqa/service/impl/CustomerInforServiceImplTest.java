package vn.edu.ptit.sqa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.ExistedException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.reportInfor.*;
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
        return new ReportInforRequest().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setMonth(new Date()).setPage(1).setSize(1)
                .setSearch("Ha Dong");
    }

    /**
     * test getReportByService method
     */

    @Test
    public void testGetReportByAddressWithNormalData() {
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
    public void testGetReportByAddressWithNullData() {
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

    /**
     * test getUnPaid service
     */

    @Test
    public void testGetUnPaidClientListWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();
        //mock usertype and customer
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        // Mock data
        List<ReportDTO> reportDTOList = new ArrayList<>();
        reportDTOList.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<ReportDTO> mockReportDtoPage = new PageImpl<>(reportDTOList);


        List<ReportDTO> mockUnpaidList = new ArrayList<>();
        mockUnpaidList.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findUnPaidCustomerPageByAddressAndTime(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, request.getSearch(), pageable)).thenReturn(mockReportDtoPage);
        when(customerRepository.findUnPaidCustomerListByAddressAndTime(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, request.getSearch())).thenReturn(mockUnpaidList);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(priceLists);

        ReportInforResponse response = customerInforService.getUnPaidClientList(request, pageable);


        assertNotNull(response);
        assertEquals(reportDTOList.size(), response.getReportDTOList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
        assertEquals(151000.0F, response.getTotalMoney());
    }


    @Test
    public void testGetUnPaidClientListWithNullReportDTOPage() {
        ReportInforRequest request = mockReportInforRequest();

        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Pageable pageable = mock(Pageable.class);
        when(customerRepository.findUnPaidCustomerPageByAddressAndTime(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, request.getSearch(), pageable)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getUnPaidClientList(request, pageable);
        });
    }

    /**
     * test setMoneyForList method
     */

    @Test
    public void testSetMoneyForListWithEmptyCustomer() {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCustomerId(1L);
        List<ReportDTO> reportDTOS = Collections.singletonList(reportDTO);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            customerInforService.setMoneyForList(reportDTOS);
        });
    }

    @Test
    public void testSetMoneyForListWithEmptyOptionalPriceList() {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCustomerId(1L);
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        List<ReportDTO> reportDTOS = Collections.singletonList(reportDTO);
        reportDTOS.get(0).setStartTime(new Date());
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ExistedException.class, () -> {
            customerInforService.setMoneyForList(reportDTOS);
        });
    }

    @Test
    public void testSetMoneyForListWithNormalData() {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCustomerId(1L);
        reportDTO.setNewWaterUsageIndex(100L);
        reportDTO.setOldWaterUsageIndex(2L);

        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        List<ReportDTO> reportDTOS = Collections.singletonList(reportDTO);
        reportDTOS.get(0).setStartTime(new Date());

        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(priceLists);

        List<ReportDTO> response = customerInforService.setMoneyForList(reportDTOS);

        assertNotNull(response);
        assertEquals(215400.0F, response.get(0).getMoneyPrice());
    }

    /**
     * test getPaid service
     */

    @Test
    public void testGetPaidClientListWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();
        //mock usertype and customer
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        // Mock data
        List<ReportDTO> reportDTOList = new ArrayList<>();
        reportDTOList.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<ReportDTO> mockReportDtoPage = new PageImpl<>(reportDTOList);


        List<ReportDTO> mockUnpaidList = new ArrayList<>();
        mockUnpaidList.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findPaidCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, request.getSearch(), pageable)).thenReturn(mockReportDtoPage);
        when(customerRepository.findAllPaidCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(mockUnpaidList);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(priceLists);

        ReportInforResponse response = customerInforService.getPaidCustomer(request, pageable);

        // Assertions
        assertNotNull(response);
        assertEquals(reportDTOList.size(), response.getReportDTOList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
        assertEquals(151000.0F, response.getTotalMoney());
    }

    @Test
    public void testGetPaidClientListWithNullReportDTOPage() {
        ReportInforRequest request = mockReportInforRequest();

        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Pageable pageable = mock(Pageable.class);
        when(customerRepository.findPaidCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, request.getSearch(), pageable)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getPaidCustomer(request, pageable);
        });
    }

    /**
     * Test getRevenue service method
     */

    @Test
    public void testGetRevenueWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();
        //mock usertype and customer
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        // Mock data
        List<RevenueDTO> revenueDTOS = new ArrayList<>();
        revenueDTOS.add(new RevenueDTO().setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<RevenueDTO> revenueDTOPage = new PageImpl<>(revenueDTOS);


        List<RevenueDTO> revenueDTOList = new ArrayList<>();
        revenueDTOList.add(new RevenueDTO().setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findAllRevenue(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(revenueDTOList);
        when(customerRepository.findRevenuePage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, pageable)).thenReturn(revenueDTOPage);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(priceLists);

        RevenueResponse response = customerInforService.getRevenue(request, pageable);

        // Assertions
        assertNotNull(response);
        assertEquals(revenueDTOS.size(), response.getRevenueDTOList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
        assertEquals(151000.0F, response.getTotalMoney());
    }

    @Test
    public void testGetRevenueWithNullReportDTOList() {
        ReportInforRequest request = mockReportInforRequest();

        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Pageable pageable = mock(Pageable.class);
        when(customerRepository.findAllRevenue(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getRevenue(request, pageable);
        });
    }

    @Test
    public void testGetRevenueWithNullCustomerOptional() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();

        // Mock data
        List<RevenueDTO> revenueDTOS = new ArrayList<>();
        revenueDTOS.add(new RevenueDTO().setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));



        List<RevenueDTO> revenueDTOList = new ArrayList<>();
        revenueDTOList.add(new RevenueDTO().setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        when(customerRepository.findAllRevenue(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(revenueDTOList);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getRevenue(request, pageable);
        });
    }

    @Test
    public void testGetRevenueWithNullPriceList() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();
        //mock usertype and customer
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        // Mock data
        List<RevenueDTO> revenueDTOS = new ArrayList<>();
        revenueDTOS.add(new RevenueDTO().setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<RevenueDTO> revenueDTOPage = new PageImpl<>(revenueDTOS);


        List<RevenueDTO> revenueDTOList = new ArrayList<>();
        revenueDTOList.add(new RevenueDTO().setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findAllRevenue(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(revenueDTOList);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(Collections.emptyList());

        // Assertions
        assertThrows(ExistedException.class, () -> {
            customerInforService.getRevenue(request, pageable);
        });
    }

    /**
     * test getDebtCustomerList service method
     */
    @Test
    public void testGetDebtCustomerWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();
        //mock usertype and customer
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        // Mock data
        List<DebtCustomerDTO> debtCustomerDTOS = new ArrayList<>();
        debtCustomerDTOS.add(new DebtCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<DebtCustomerDTO> debtCustomerDTOPage = new PageImpl<>(debtCustomerDTOS);


        List<DebtCustomerDTO> debtCustomerDTOList = new ArrayList<>();
        debtCustomerDTOList.add(new DebtCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findAllDebtCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(debtCustomerDTOList);
        when(customerRepository.findDebtCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, pageable)).thenReturn(debtCustomerDTOPage);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(priceLists);

        DebtReportResponse response = customerInforService.getDebtCustomerList(request, pageable);

        // Assertions
        assertNotNull(response);
        assertEquals(debtCustomerDTOS.size(), response.getDebtCustomerList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
        assertEquals(151000.0F, response.getTotalDebtNumber());
    }

    @Test
    public void testGetRevenueWithNullDebtCustomerDTOList() {
        ReportInforRequest request = mockReportInforRequest();

        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Pageable pageable = mock(Pageable.class);
        when(customerRepository.findDebtCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, pageable)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getDebtCustomerList(request, pageable);
        });
    }

    @Test
    public void testGetDebtCustomerListWithNullCustomerOptional() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();

        // Mock data
        List<DebtCustomerDTO> debtCustomerDTOS = new ArrayList<>();
        debtCustomerDTOS.add(new DebtCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<DebtCustomerDTO> debtCustomerDTOPage = new PageImpl<>(debtCustomerDTOS);


        List<DebtCustomerDTO> debtCustomerDTOList = new ArrayList<>();
        debtCustomerDTOList.add(new DebtCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findAllDebtCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(debtCustomerDTOList);
        when(customerRepository.findDebtCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, pageable)).thenReturn(debtCustomerDTOPage);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());


        // Assertions
        assertThrows(NotFoundException.class, () -> {
            customerInforService.getDebtCustomerList(request, pageable);
        });
    }

    @Test
    public void testGetDebtCustomerWithNullPriceList() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();
        //mock usertype and customer
        UserType userType = new UserType();
        userType.setId(1);
        Customer customer = new Customer().setId(1L).setUserType(userType);
        // Mock data
        List<DebtCustomerDTO> debtCustomerDTOS = new ArrayList<>();
        debtCustomerDTOS.add(new DebtCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        Page<DebtCustomerDTO> debtCustomerDTOPage = new PageImpl<>(debtCustomerDTOS);


        List<DebtCustomerDTO> debtCustomerDTOList = new ArrayList<>();
        debtCustomerDTOList.add(new DebtCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findAllDebtCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(debtCustomerDTOList);
        when(customerRepository.findDebtCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, pageable)).thenReturn(debtCustomerDTOPage);
        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.of(customer));
        when(priceListRepo.findByUserType(any(Integer.class), any(Date.class))).thenReturn(Collections.emptyList());

        // Assertions
        assertThrows(ExistedException.class, () -> {
            customerInforService.getDebtCustomerList(request, pageable);
        });
    }

    /**
     * test getDebtCustomerNumber
     */
    @Test
    public void testGetDebtCustomerNumberWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        // Mock data
        List<ReportDTO> debtCustomerDTOS = new ArrayList<>();
        debtCustomerDTOS.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));


        List<ReportDTO> reportDTOS = new ArrayList<>();
        reportDTOS.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));

        //mock List<PriceList> and List<PriceScale>
        List<PriceList> priceLists = new ArrayList<>();
        PriceList priceList = new PriceList();
        priceList.setListPriceScales(setPriceScales());
        priceLists.add(priceList);

        when(customerRepository.findByAddressListAll(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(reportDTOS);
        when(customerRepository.findDebtCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(debtCustomerDTOS);


        DebtReportDTO response = customerInforService.getDebtCustomerNumber(request);

        // Assertions
        assertNotNull(response);
        assertEquals(debtCustomerDTOS.size(), response.getDebtNum());
        assertEquals(reportDTOS.size(), response.getAllCustomerNum());
        assertEquals("100.0%", response.getPercent());
    }

    @Test
    public void testGetDebtCustomerNumberWithCustomerAllListIsEmpty() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        // Mock data
        List<ReportDTO> debtCustomerDTOS = new ArrayList<>();
        debtCustomerDTOS.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));


        List<ReportDTO> reportDTOS = new ArrayList<>();


        when(customerRepository.findByAddressListAll(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(reportDTOS);
        when(customerRepository.findDebtCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(debtCustomerDTOS);


        DebtReportDTO response = customerInforService.getDebtCustomerNumber(request);

        // Assertions
        assertNotNull(response);
        assertEquals(debtCustomerDTOS.size(), response.getDebtNum());
        assertEquals(reportDTOS.size(), response.getAllCustomerNum());
        assertEquals("0.0%", response.getPercent());
    }
    /**
     * get NewCustomerList servce method
     */
    @Test
    public void testGetNewCustomerWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Pageable pageable = Pageable.unpaged();

        // Mock data
        List<NewCustomerDTO> newCustomerDTOS = new ArrayList<>();
        newCustomerDTOS.add(new NewCustomerDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setCreateTime(new Date())
                .setCustomerId(1L));


        Page<NewCustomerDTO> newCustomerDTOPage = new PageImpl<>(newCustomerDTOS);


        when(customerRepository.findNewCustomerPage(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end, pageable)).thenReturn(newCustomerDTOPage);


        newCustomerResponse response = customerInforService.getNewCustomerList(request, pageable);

        // Assertions
        assertNotNull(response);
        assertEquals(newCustomerDTOS.size(), response.getNewCustomerDTOList().size());
        assertEquals(1, response.getPageDto().getTotalElements());
    }

    @Test
    public void testGetNewCustomerNumberWithNormalData() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Date nullDate = null;
        // Mock data
        List<ReportDTO> newCustomerDTOS = new ArrayList<>();
        newCustomerDTOS.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));


        List<ReportDTO> reportDTOS = new ArrayList<>();
        reportDTOS.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));


        when(customerRepository.findCustomerListByAddress(request.getProvine(), request.getDistrict(),
                request.getWard(), end)).thenReturn(reportDTOS);
        when(customerRepository.findNewCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(newCustomerDTOS);


        NewCustomerReportDTO response = customerInforService.getNewCustomerNumber(request);

        // Assertions
        assertNotNull(response);
        assertEquals(newCustomerDTOS.size(), response.getNewCustomerNum());
        assertEquals(reportDTOS.size(), response.getAllCustomerNum());
        assertEquals("100.0%", response.getPercent());
    }

    @Test
    public void testGetNewCustomerNumberWithAllCustomerListIsEmpty() {
        ReportInforRequest request = mockReportInforRequest();
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());

        Date nullDate = null;
        // Mock data
        List<ReportDTO> newCustomerDTOS = new ArrayList<>();
        newCustomerDTOS.add(new ReportDTO().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date())
                .setCustomerId(1L)
                .setNewWaterUsageIndex(100L)
                .setOldWaterUsageIndex(30L));


        List<ReportDTO> reportDTOS = new ArrayList<>();

        when(customerRepository.findCustomerListByAddress(request.getProvine(), request.getDistrict(),
                request.getWard(), end)).thenReturn(reportDTOS);
        when(customerRepository.findNewCustomer(request.getProvine(), request.getDistrict(),
                request.getWard(), start, end)).thenReturn(newCustomerDTOS);


        NewCustomerReportDTO response = customerInforService.getNewCustomerNumber(request);

        // Assertions
        assertNotNull(response);
        assertEquals(newCustomerDTOS.size(), response.getNewCustomerNum());
        assertEquals(reportDTOS.size(), response.getAllCustomerNum());
        assertEquals("0.0%", response.getPercent());
    }
}
