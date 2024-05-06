package vn.edu.ptit.sqa.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.edu.ptit.sqa.model.EmailDetailAM;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;
import vn.edu.ptit.sqa.service.EmailDetailService;

@ContextConfiguration(classes = {EmailDetailController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EmailDetailControllerDiffblueTest {
    @Autowired
    private EmailDetailController emailDetailController;

    @MockBean
    private EmailDetailService emailDetailService;

    /**
     * Method under test:
     * {@link EmailDetailController#createEmailDetail(EmailDetailAM)}
     */
    @Test
    void testCreateEmailDetail() throws Exception {
        // Arrange
        when(emailDetailService.createEmailDetail(Mockito.<EmailDetailAM>any())).thenReturn(true);

        ReportInforRequest reportInforRequest = new ReportInforRequest();
        reportInforRequest.setDistrict("Hà Đông");
        reportInforRequest.setInvoiceStatus("unpaid");
        reportInforRequest.setMonth(Date.from(LocalDate.of(2024, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reportInforRequest.setPage(1);
        reportInforRequest.setProvine("Hà Nội");
        reportInforRequest.setSearch("");
        reportInforRequest.setSize(3);
        reportInforRequest.setWard("Van Quan");

        EmailDetailAM emailDetailAM = new EmailDetailAM();
        emailDetailAM.setAttachmentIds(new ArrayList<>());
        emailDetailAM.setReportInforRequest(reportInforRequest);
        emailDetailAM.setTemplateId(1);
        String content = (new ObjectMapper()).writeValueAsString(emailDetailAM);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/emailDetail/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(emailDetailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("OK"));
    }
}
