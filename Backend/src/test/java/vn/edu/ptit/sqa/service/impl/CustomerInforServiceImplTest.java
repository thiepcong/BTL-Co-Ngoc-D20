//package vn.edu.ptit.sqa.service.impl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import vn.edu.ptit.sqa.exception.NotFoundException;
//import vn.edu.ptit.sqa.model.reportInfor.ReportDTO;
//import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;
//import vn.edu.ptit.sqa.model.reportInfor.ReportInforResponse;
//import vn.edu.ptit.sqa.repository.CustomerRepository;
//import vn.edu.ptit.sqa.repository.PriceListRepo;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class CustomerInforServiceImplTest {
//    @InjectMocks
//    private CustomerInforServiceImpl customerInforService;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private PriceListRepo priceListRepo;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    private Pageable setPage(int a, int b){
//        Pageable pageable = PageRequest.of(a-1, b);
//        return pageable;
//    }
//
//    private ReportInforRequest mockReportInforRequest(){
//        return new ReportInforRequest().setProvine("Ha Noi")
//                .setDistrict("Ha Dong")
//                .setWard("Van Quan")
//                .setMonth(new Date())
//                .setPage(1)
//                .setSize(1)
//                .setSearch("Ha Dong");
//    }
//
//
//    @Test
//    public void testGetReportByAddress_ListNotNull() {
//        // Mock data
//        ReportInforRequest request = mockReportInforRequest();
//        Pageable pageable = setPage(request.getPage(), request.getSize());
//        List<ReportDTO> reportDTOList = new ArrayList<>();
//        reportDTOList.add(new ReportDTO());
//        Page<ReportDTO> page = new PageImpl<>(reportDTOList);
//
//        when(customerRepository.findByAddressPage(anyString(), anyString(), anyString(), anyString(), any(Pageable.class))).thenReturn(page);
//
//        // Test method
//        ReportInforResponse response = customerInforService.getReportByAddress(request, pageable);
//
//        // Assertion
//        assertNotNull(response);
//        assertEquals(reportDTOList, response.getReportDTOList());
//        assertNotNull(response.getPageDto());
//    }
//
//    @Test
//    public void testGetReportByAddress_ListNull() {
//        // Arrange
//        ReportInforRequest request = mockReportInforRequest();
//        Pageable pageable = setPage(request.getPage(), request.getSize());
//        Page<ReportDTO> reportDtoPage = Mockito.mock(Page.class);
//        when(reportDtoPage.getContent()).thenReturn(null);
//        when(customerRepository.findByAddressPage(anyString(), anyString(),
//                anyString(), anyString(), any(Pageable.class)))
//                .thenReturn(reportDtoPage);
//
//        // Act & Assert
//        assertThrows(NotFoundException.class, () -> {
//            customerInforService.getReportByAddress(request, pageable);
//        });
//    }
//
//    @Test
//    public void testGetUnPaidClientList_NullList() {
//        // Tạo mock cho phương thức trả về null
//        when(customerRepository.findUnPaidCustomerPageByAddressAndTime(anyString(),
//                anyString(), anyString(), any(Date.class), any(Date.class),
//                anyString(), any(Pageable.class)))
//                .thenReturn(null);
//
//        // Kiểm tra xem phương thức có ném ra NotFoundException hay không
//        assertThrows(NotFoundException.class, () -> {
//            customerInforService.getUnPaidClientList(new ReportInforRequest(), PageRequest.of(0, 10));
//        });
//    }
//
//}
