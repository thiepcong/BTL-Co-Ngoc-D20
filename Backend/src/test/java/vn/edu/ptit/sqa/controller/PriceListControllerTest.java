package vn.edu.ptit.sqa.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.edu.ptit.sqa.config.ConfigSecurity;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.*;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.service.PriceListService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

@ContextConfiguration(classes = {ConfigSecurity.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(PriceListController.class)
class PriceListControllerTest {

    @MockBean
    private PriceListService priceListService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Method under test:
     * {@link PriceListController#createPriceList(Integer, PriceListRequest)}
     */
    @Test
    void testCreatePriceList_Valid() throws Exception {
        // Arrange
        PriceListResponse priceListResponse = new PriceListResponse();
        priceListResponse.setId(32);
        priceListResponse.setStatus(1);
        UserType userType = new UserType();
        userType.setId(1);
        userType.setTypeName("Ho ngheo");
        priceListResponse.setUserType(ConverterUtil.mappingToObject(userType, UserTypeResponse.class));
        priceListResponse.setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        priceListResponse.setStatus(1);
        List<PriceScaleResponse> priceScaleRequestsResponse = new ArrayList<>();
        priceScaleRequestsResponse.add(new PriceScaleResponse(163, 0, 50, 2100));
        priceScaleRequestsResponse.add(new PriceScaleResponse(164, 51, 100, 2300));
        priceScaleRequestsResponse.add(new PriceScaleResponse(165, 101, 150, 2500));
        priceListResponse.setListPriceScales(priceScaleRequestsResponse);

        when(priceListService.createPriceList(Mockito.<Integer>any(), Mockito.<PriceListRequest>any()))
                .thenReturn(priceListResponse);

        PriceListRequest priceListRequest = new PriceListRequest();
        priceListRequest
                .setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        priceScaleRequests.add(new PriceScaleRequest(0, 50, 2100));
        priceScaleRequests.add(new PriceScaleRequest(51, 100, 2300));
        priceScaleRequests.add(new PriceScaleRequest(101, 150, 2500));

        priceListRequest.setListPriceScales(priceScaleRequests);
        String content = (new ObjectMapper()).writeValueAsString(priceListRequest);
        mockMvc.perform(post("/api/price_list/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(print());
    }

    /**
     * Method under test:
     * {@link PriceListController#createPriceList(Integer, PriceListRequest)}
     */
    @Test
    void testCreatePriceList_UserTypeNotFound() throws Exception {
        PriceListRequest priceListRequest = new PriceListRequest();
        priceListRequest
                .setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        priceScaleRequests.add(new PriceScaleRequest(0, 50, 2100));
        priceScaleRequests.add(new PriceScaleRequest(51, 100, 2300));
        priceScaleRequests.add(new PriceScaleRequest(101, 150, 2500));

        priceListRequest.setListPriceScales(priceScaleRequests);
        when(priceListService.createPriceList(1000, priceListRequest))
                .thenThrow(new NotFoundException("customer type id 1000"));

        String content = (new ObjectMapper()).writeValueAsString(priceListRequest);
        mockMvc.perform(post("/api/price_list/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andDo(print());
    }

    @Test
    void testCreatePriceList_ChiSoDauLonHonChiSoCuoiBacTruoc_LonHon1DonVi() throws Exception {
        PriceListRequest priceListRequest = new PriceListRequest();
        priceListRequest
                .setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        priceScaleRequests.add(new PriceScaleRequest(0, 50, 2100));
        priceScaleRequests.add(new PriceScaleRequest(52, 100, 2300));
        priceScaleRequests.add(new PriceScaleRequest(101, 150, 2500));

        priceListRequest.setListPriceScales(priceScaleRequests);
        when(priceListService.createPriceList(1, priceListRequest))
                .thenThrow(new BadRequestException("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị"));

        String content = (new ObjectMapper()).writeValueAsString(priceListRequest);
        mockMvc.perform(post("/api/price_list/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị"))
                .andDo(print());
    }

    @Test
    void testCreatePriceList_ChiSoDauBeHonHoacBangChiSoCuoiBacTruoc() throws Exception {
        PriceListRequest priceListRequest = new PriceListRequest();
        priceListRequest
                .setApplyDate(Date.from(LocalDate.of(2024, 5, 6).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        priceScaleRequests.add(new PriceScaleRequest(0, 50, 2100));
        priceScaleRequests.add(new PriceScaleRequest(50, 100, 2300));
        priceScaleRequests.add(new PriceScaleRequest(101, 150, 2500));

        priceListRequest.setListPriceScales(priceScaleRequests);
        when(priceListService.createPriceList(1, priceListRequest))
                .thenThrow(new BadRequestException("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị"));

        String content = (new ObjectMapper()).writeValueAsString(priceListRequest);
        mockMvc.perform(post("/api/price_list/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị"))
                .andDo(print());
    }

    @Test
    void testCreatePriceList_ChiSoDauLonHonHoacBangChiSoCuoi() throws Exception {
        PriceListRequest priceListRequest = new PriceListRequest();
        priceListRequest
                .setApplyDate(Date.from(LocalDate.of(2024, 5, 6).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        priceScaleRequests.add(new PriceScaleRequest(0, 50, 2100));
        priceScaleRequests.add(new PriceScaleRequest(51, 100, 2300));
        priceScaleRequests.add(new PriceScaleRequest(101, 101, 2500));

        priceListRequest.setListPriceScales(priceScaleRequests);
        when(priceListService.createPriceList(1, priceListRequest))
                .thenThrow(new BadRequestException("Chỉ số đầu phải bé hơn chỉ số cuối"));

        String content = (new ObjectMapper()).writeValueAsString(priceListRequest);
        mockMvc.perform(post("/api/price_list/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Chỉ số đầu phải bé hơn chỉ số cuối"))
                .andDo(print());
    }

    @Test
    void testGetPriceListById_PriceListNotFound() throws Exception {

        when(priceListService.getPriceListResponseById(100))
                .thenThrow(new NotFoundException("PriceList id 100"));
        mockMvc.perform(get("/api/price_list/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("PriceList id 100 Not found data"))
                .andDo(print());
    }

    @Test
    void testGetPriceListById_Valid() throws Exception {
        PriceListResponse priceListResponse = new PriceListResponse();
        priceListResponse.setId(2);
        priceListResponse.setStatus(1);
        UserType userType = new UserType();
        userType.setId(1);
        userType.setTypeName("Ho ngheo");
        priceListResponse.setUserType(ConverterUtil.mappingToObject(userType, UserTypeResponse.class));
        priceListResponse.setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        priceListResponse.setStatus(1);
        List<PriceScaleResponse> priceScaleRequestsResponse = new ArrayList<>();
        priceScaleRequestsResponse.add(new PriceScaleResponse(163, 0, 50, 2100));
        priceScaleRequestsResponse.add(new PriceScaleResponse(164, 51, 100, 2300));
        priceScaleRequestsResponse.add(new PriceScaleResponse(165, 101, 150, 2500));
        priceListResponse.setListPriceScales(priceScaleRequestsResponse);
        when(priceListService.getPriceListResponseById(100))
                .thenReturn(priceListResponse);
        mockMvc.perform(get("/api/price_list/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void testGetAllPriceList() throws Exception {
        PriceListResponse priceListResponse = new PriceListResponse();
        priceListResponse.setId(2);
        priceListResponse.setStatus(1);
        UserType userType = new UserType();
        userType.setId(1);
        userType.setTypeName("Ho ngheo");
        priceListResponse.setUserType(ConverterUtil.mappingToObject(userType, UserTypeResponse.class));
        priceListResponse.setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        priceListResponse.setStatus(1);
        List<PriceScaleResponse> priceScaleRequestsResponse = new ArrayList<>();
        priceScaleRequestsResponse.add(new PriceScaleResponse(163, 0, 50, 2100));
        priceScaleRequestsResponse.add(new PriceScaleResponse(164, 51, 100, 2300));
        priceScaleRequestsResponse.add(new PriceScaleResponse(165, 101, 150, 2500));
        priceListResponse.setListPriceScales(priceScaleRequestsResponse);

        PriceListResponse priceListResponse2 = new PriceListResponse();
        priceListResponse2.setId(3);
        priceListResponse2.setStatus(1);
        UserType userType2 = new UserType();
        userType2.setId(3);
        userType2.setTypeName("Ho ngheo");
        priceListResponse2.setUserType(ConverterUtil.mappingToObject(userType, UserTypeResponse.class));
        priceListResponse2.setApplyDate(Date.from(LocalDate.of(2024, 6, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        priceListResponse2.setStatus(1);
        List<PriceScaleResponse> priceScaleRequestsResponse2 = new ArrayList<>();
        priceScaleRequestsResponse2.add(new PriceScaleResponse(166, 0, 50, 2300));
        priceScaleRequestsResponse2.add(new PriceScaleResponse(167, 51, 100, 2500));
        priceScaleRequestsResponse2.add(new PriceScaleResponse(168, 101, 150, 2700));
        priceListResponse2.setListPriceScales(priceScaleRequestsResponse2);

        List<PriceListResponse> priceListResponses = new ArrayList<>();
        priceListResponses.add(priceListResponse);
        priceListResponses.add(priceListResponse2);
        DataTableResults<PriceListResponse> dataTableResults = new DataTableResults<>();
        dataTableResults.setCurrentPage(0);
        dataTableResults.setTotalItems(2L);
        dataTableResults.setTotalPages(1);
        dataTableResults.setData(priceListResponses);

        PaginationRequest paginationRequest = new PaginationRequest(true, 0, 2);
        when(priceListService.getAllPriceList(paginationRequest))
                .thenReturn(dataTableResults);
        mockMvc.perform(get("/api/price_list?pageNum=0&pageSize=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
