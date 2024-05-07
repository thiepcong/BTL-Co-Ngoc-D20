package vn.edu.ptit.sqa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import vn.edu.ptit.sqa.model.pagination.PageDto;
import vn.edu.ptit.sqa.model.reportInfor.*;
import vn.edu.ptit.sqa.service.CustomerInforService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportInforControllerTest.class)
@ContextConfiguration(classes = {ReportInforController.class})
@AutoConfigureMockMvc(addFilters = false)
public class ReportInforControllerTest {

    @Autowired
    ReportInforController reportInforController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerInforService customerInforService;

    private static final String END_POINT_PATH = "/api/report";

    private ReportInforRequest mockReportInforRequest() {
        return new ReportInforRequest().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setMonth(new Date())
                .setPage(1)
                .setSize(10)
                .setSearch(" ");
    }

    /**
     * Test api /api/report/getDebtCustomerList
     */

    @Test
    public void testGetDebtClientListWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        DebtReportResponse mockResponse = new DebtReportResponse();
        List<DebtCustomerDTO> debtCustomerList = new ArrayList<>();
        debtCustomerList.add(new DebtCustomerDTO().setCustomerId(1L)
                .setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date()));


        Page<DebtCustomerDTO> reportDtoPage = new PageImpl<>(debtCustomerList, pageable, debtCustomerList.size());

        mockResponse.setDebtCustomerList(debtCustomerList);
        mockResponse.setPageDto(PageDto.populatePageDto(reportDtoPage));

        when(customerInforService.getDebtCustomerList(request, pageable))
                .thenReturn(mockResponse);


        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getDebtCustomerList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        DebtReportResponse response = objectMapper.readValue(responseBody, DebtReportResponse.class);

        assertNotNull(response);
        assertNotNull(response.getDebtCustomerList());
        assertEquals(mockResponse.getPageDto(), response.getPageDto());
        assertEquals(response.getDebtCustomerList().size(), response.getPageDto().getTotalElements());

    }

    @Test
    public void testGetDebtClientNumberWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();

        DebtReportDTO mockResponse = new DebtReportDTO();
        mockResponse.setDebtNum(1);
        mockResponse.setAllCustomerNum(5);
        mockResponse.setPercent("20%");

        when(customerInforService.getDebtCustomerNumber(request))
                .thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getDebtCustomer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DebtReportDTO response = objectMapper.readValue(responseBody, DebtReportDTO.class);

        assertNotNull(response);
        assertEquals(1, response.getDebtNum());
        assertEquals(5, response.getAllCustomerNum());
        assertEquals("20%", response.getPercent());

    }

    @Test
    public void testGetDebtClientNumberWithAllNumberIsZero() throws Exception {
        ReportInforRequest request = mockReportInforRequest();

        DebtReportDTO mockResponse = new DebtReportDTO();
        mockResponse.setDebtNum(1);
        mockResponse.setAllCustomerNum(0);
        mockResponse.setPercent("0%");

        when(customerInforService.getDebtCustomerNumber(request))
                .thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getDebtCustomer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DebtReportDTO response = objectMapper.readValue(responseBody, DebtReportDTO.class);

        assertNotNull(response);
        assertEquals(1, response.getDebtNum());
        assertEquals(0, response.getAllCustomerNum());
        assertEquals("0%", response.getPercent());

    }

    /**
     * Test api /api/report/getNewCustomerList
     */

    @Test
    public void testGetNewCustomerListWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        newCustomerResponse mockResponse = new newCustomerResponse();
        List<NewCustomerDTO> newCustomerDTOList = new ArrayList<>();
        newCustomerDTOList.add(new NewCustomerDTO().setCustomerId(1L)
                .setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setCreateTime(new Date()));


        Page<NewCustomerDTO> reportDtoPage = new PageImpl<>(newCustomerDTOList, pageable, newCustomerDTOList.size());

        mockResponse.setNewCustomerDTOList(newCustomerDTOList);
        mockResponse.setPageDto(PageDto.populatePageDto(reportDtoPage));

        when(customerInforService.getNewCustomerList(request, pageable))
                .thenReturn(mockResponse);


        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getNewCustomerList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        newCustomerResponse response = objectMapper.readValue(responseBody, newCustomerResponse.class);

        assertNotNull(response);
        assertNotNull(response.getNewCustomerDTOList());
        assertEquals(mockResponse.getPageDto(), response.getPageDto());
        assertEquals(response.getNewCustomerDTOList().size(), response.getPageDto().getTotalElements());

    }

    @Test
    public void testGetNewCustomerNumberWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();

        NewCustomerReportDTO mockResponse = new NewCustomerReportDTO();
        mockResponse.setNewCustomerNum(1);
        mockResponse.setAllCustomerNum(5);
        mockResponse.setPercent("20%");

        when(customerInforService.getNewCustomerNumber(request))
                .thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getNewCustomers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        NewCustomerReportDTO response = objectMapper.readValue(responseBody, NewCustomerReportDTO.class);

        assertNotNull(response);
        assertEquals(1, response.getNewCustomerNum());
        assertEquals(5, response.getAllCustomerNum());
        assertEquals("20%", response.getPercent());

    }

    @Test
    public void testGetNewCustomerNumberWithAllNumberIsZero() throws Exception {
        ReportInforRequest request = mockReportInforRequest();

        NewCustomerReportDTO mockResponse = new NewCustomerReportDTO();
        mockResponse.setNewCustomerNum(1);
        mockResponse.setAllCustomerNum(0);
        mockResponse.setPercent("0%");

        when(customerInforService.getNewCustomerNumber(request))
                .thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getNewCustomers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        NewCustomerReportDTO response = objectMapper.readValue(responseBody, NewCustomerReportDTO.class);

        assertNotNull(response);
        assertEquals(1, response.getNewCustomerNum());
        assertEquals(0, response.getAllCustomerNum());
        assertEquals("0%", response.getPercent());

    }

    /**
     * Test api /api/report/getNewCustomerList
     */

    @Test
    public void testGetRevenueListWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        RevenueResponse mockResponse = new RevenueResponse();
        List<RevenueDTO> revenueDTOList = new ArrayList<>();
        revenueDTOList.add(new RevenueDTO().setCustomerId(1L)
                .setProvince("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStart(new Date())
                .setMoneyNumber(215560.0F));


        Page<RevenueDTO> reportDtoPage = new PageImpl<>(revenueDTOList, pageable, revenueDTOList.size());

        mockResponse.setRevenueDTOList(revenueDTOList);
        mockResponse.setPageDto(PageDto.populatePageDto(reportDtoPage));
        mockResponse.setTotalMoney(revenueDTOList.get(0).getMoneyNumber());

        when(customerInforService.getRevenue(request, pageable))
                .thenReturn(mockResponse);


        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH + "/getRevenueList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        RevenueResponse response = objectMapper.readValue(responseBody, RevenueResponse.class);

        assertNotNull(response);
        assertNotNull(response.getRevenueDTOList());
        assertEquals(mockResponse.getPageDto(), response.getPageDto());
        assertEquals(response.getRevenueDTOList().size(), response.getPageDto().getTotalElements());
        assertEquals(215560.0F, response.getTotalMoney());
    }
}
