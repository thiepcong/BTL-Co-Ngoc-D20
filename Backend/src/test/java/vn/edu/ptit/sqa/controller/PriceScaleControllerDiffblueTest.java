package vn.edu.ptit.sqa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import vn.edu.ptit.sqa.service.PriceScaleService;

@ContextConfiguration(classes = {PriceScaleController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PriceScaleControllerDiffblueTest {
    @Autowired
    private PriceScaleController priceScaleController;

    @MockBean
    private PriceScaleService priceScaleService;

    /**
     * Method under test:
     * {@link PriceScaleController#createPriceScale(Integer, List)}
     */
    @Test
    void testCreatePriceScale() throws Exception {

        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/price_scale/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON);
        ArrayList<PriceScaleRequest> priceScaleRequestList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(priceScaleRequestList));
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(priceScaleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }

    @Test
    public void createPriceScale_HappyPath() {
        PriceScaleRequest priceScaleRequest = new PriceScaleRequest();
        priceScaleRequest.setStartIndex(0);
        priceScaleRequest.setEndIndex(50);
        priceScaleRequest.setPrice(2100.0f);
        List<PriceScaleResponse> priceScaleResponseList = new ArrayList<>();
        when(priceScaleService.createPriceScale(anyInt(), any())).thenReturn(priceScaleResponseList);

        ResponseEntity<?> result = priceScaleController.createPriceScale(1, Collections.singletonList(priceScaleRequest));

        assertEquals(ResponseEntity.ok(Arrays.asList(priceScaleRequest)), result);
    }

    /**
     * Method under test: {@link PriceScaleController#getPriceScaleById(Integer)}
     */
    @Test
    void testGetPriceScaleById() throws Exception {
        // Arrange
        when(priceScaleService.getPriceScaleById(Mockito.<Integer>any())).thenReturn(new PriceScaleResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/price_scale/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(priceScaleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"startIndex\":null,\"endIndex\":null,\"price\":0.0}"));
    }
}
