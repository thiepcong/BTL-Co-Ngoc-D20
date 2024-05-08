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
import vn.edu.ptit.sqa.model.reportInfor.ReportDTO;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforResponse;
import vn.edu.ptit.sqa.service.CustomerInforService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerInforController.class)
@ContextConfiguration(classes = {CustomerInforController.class})
@AutoConfigureMockMvc(addFilters = false)
public class CustomerInforControllerTest {

    @Autowired
    CustomerInforController customerInforController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerInforService service;

    private static final String END_POINT_PATH = "/api/customer";

    private ReportInforRequest mockReportInforRequest() {
        return new ReportInforRequest().setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setMonth(new Date())
                .setPage(1)
                .setSize(10)
                .setSearch(" ");
    }

    private ReportInforResponse mockResponse(){
        ReportInforResponse response = new ReportInforResponse();
        List<ReportDTO> reportDTOList = new ArrayList<>();
        reportDTOList.add(new ReportDTO().setCustomerId(1L)
                .setProvine("Ha Noi")
                .setDistrict("Ha Dong")
                .setWard("Van Quan")
                .setStartTime(new Date()));
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReportDTO> reportDtoPage = new PageImpl<>(reportDTOList, pageable, reportDTOList.size());
        response.setReportDTOList(reportDTOList);
        response.setPageDto(PageDto.populatePageDto(reportDtoPage));
        return response;
    }

    /**
     *
     * Test api /api/customer/listByAdsress
     */

    @Test
    public void testGetClientListByAddressWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        ReportInforResponse mockResponse = mockResponse();

        when(service.getReportByAddress(request, pageable))
                .thenReturn(mockResponse);


        MvcResult result = mockMvc.perform(
                post(END_POINT_PATH +"/listByAdsress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        ReportInforResponse response = objectMapper.readValue(responseBody, ReportInforResponse.class);

        assertNotNull(response);
        assertNotNull(response.getReportDTOList());
        assertEquals(mockResponse.getPageDto(), response.getPageDto());
        assertEquals(response.getReportDTOList().size(), response.getPageDto().getTotalElements());

    }


    /**
     *
     * Test api /api/customer/UnPaidListByAdsress
     */

    @Test
    public void testGetUnpaidClientListWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        ReportInforResponse mockResponse = mockResponse();

        when(service.getUnPaidClientList(request, pageable))
                .thenReturn(mockResponse);


        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH +"/UnPaidListByAdsress")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        ReportInforResponse response = objectMapper.readValue(responseBody, ReportInforResponse.class);

        assertNotNull(response);
        assertNotNull(response.getReportDTOList());
        assertEquals(mockResponse.getPageDto(), response.getPageDto());
        assertEquals(response.getReportDTOList().size(), response.getPageDto().getTotalElements());

    }

    /**
     *
     * Test api /api/customer/UnPaidListByAdsress
     */

    @Test
    public void testGetPaidClientListWithNormalData() throws Exception {
        ReportInforRequest request = mockReportInforRequest();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        ReportInforResponse mockResponse = mockResponse();

        when(service.getPaidCustomer(request, pageable))
                .thenReturn(mockResponse);


        MvcResult result = mockMvc.perform(
                        post(END_POINT_PATH +"/getPaidCustomers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        ReportInforResponse response = objectMapper.readValue(responseBody, ReportInforResponse.class);

        assertNotNull(response);
        assertNotNull(response.getReportDTOList());
        assertEquals(mockResponse.getPageDto(), response.getPageDto());
        assertEquals(response.getReportDTOList().size(), response.getPageDto().getTotalElements());

    }
}
