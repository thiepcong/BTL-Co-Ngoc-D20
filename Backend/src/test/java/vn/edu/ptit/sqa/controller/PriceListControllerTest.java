package vn.edu.ptit.sqa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.edu.ptit.sqa.entity.UserType;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.*;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.service.PriceListService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

@ContextConfiguration(classes = {PriceListController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@WebMvcTest(PriceListController.class)
class PriceListControllerTest {
    @Autowired
    private PriceListController priceListController;

    @MockBean
    private PriceListService priceListService;

    @Autowired
    MockMvc mockMvc;
    /**
     * Method under test:
     * {@link PriceListController#createPriceList(Integer, PriceListRequest)}
     */
    @Test
    void testCreatePriceList() throws Exception {
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
        MockHttpServletRequestBuilder requestBuilder = post("/api/price_list/1", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(priceListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":32,\"userType\":{" +
                                "\"id\":1," +
                                "\"typeName\":\"Ho ngheo\""+
                                "}," +
                                "\"applyDate\":1711929600000," +
                                "\"status\":1,"+
                                "\"listPriceScales\":[" +
                                "{" +
                                "\"id\":163," +
                                "\"startIndex\":0," +
                                "\"endIndex\":50," +
                                "\"price\":2100.0" +
                                "}," +
                                "{" +
                                "\"id\":164," +
                                "\"startIndex\":51," +
                                "\"endIndex\":100," +
                                "\"price\":2300.0" +
                                "}," +
                                "{" +
                                "\"id\":165," +
                                "\"startIndex\":101," +
                                "\"endIndex\":150," +
                                "\"price\":2500.0" +
                                "}]}"));
    }

    /**
     * Method under test:
     * {@link PriceListController#createPriceList(Integer, PriceListRequest)}
     */
    @Test
    void testCreatePriceList_UserTypeNotFound() throws Exception {
//        // Arrange
        ResponseError responseError = new ResponseError();
        responseError.setStatus("NOT_FOUND");
        responseError.setMessage("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị");
        responseError.setErrors(new ArrayList<>());
        when(priceListService.createPriceList(Mockito.<Integer>any(), Mockito.<PriceListRequest>any()))
                .thenThrow(NotFoundException.class);

        PriceListRequest priceListRequest = new PriceListRequest();
        priceListRequest
                .setApplyDate(Date.from(LocalDate.of(2024, 4, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        List<PriceScaleRequest> priceScaleRequests = new ArrayList<>();
        priceScaleRequests.add(new PriceScaleRequest(0, 50, 2100));
        priceScaleRequests.add(new PriceScaleRequest(55, 100, 2300));
        priceScaleRequests.add(new PriceScaleRequest(101, 150, 2500));

        priceListRequest.setListPriceScales(priceScaleRequests);
        String content = (new ObjectMapper()).writeValueAsString(priceListRequest);
        mockMvc.perform(post("/api/price_list/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/price_list/1000", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//
//        // Act and Assert
//        MockMvcBuilders.standaloneSetup(priceListController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST")).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//                assertEquals("{\"status\":\"BAD_REQUEST\",\"message\":\"Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị\",\"errors\":[]}",
//                result.getResponse().getContentAsString());
    }

    /**
     * Method under test:
     * {@link PriceListController#getAllPriceList(Integer, Integer)}
     */
    @Test
    void testGetAllPriceList() throws Exception {
        // Arrange
        when(priceListService.getAllPriceList(Mockito.<PaginationRequest>any())).thenReturn(new DataTableResults<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/price_list");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(priceListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"currentPage\":null,\"totalPages\":null,\"totalItems\":null,\"data\":null}"));
    }

    /**
     * Method under test: {@link PriceListController#getPriceListById(Integer)}
     */
    @Test
    void testGetPriceListById() throws Exception {
        // Arrange
        when(priceListService.getPriceListResponseById(Mockito.<Integer>any())).thenReturn(new PriceListResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/price_list/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(priceListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"userType\":null,\"applyDate\":null,\"status\":null,\"listPriceScales\":null}"));
    }

    /**
     * Method under test: {@link PriceListController#getPriceListById(Integer)}
     */
    @Test
    void testGetPriceListById2() throws Exception {
        // Arrange
        when(priceListService.getAllPriceList(Mockito.<PaginationRequest>any())).thenReturn(new DataTableResults<>());
        when(priceListService.getPriceListResponseById(Mockito.<Integer>any())).thenReturn(new PriceListResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/price_list/{id}", "",
                "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(priceListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"currentPage\":null,\"totalPages\":null,\"totalItems\":null,\"data\":null}"));
    }
}
