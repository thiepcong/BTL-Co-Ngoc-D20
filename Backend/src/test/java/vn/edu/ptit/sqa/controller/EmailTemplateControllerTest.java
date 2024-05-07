package vn.edu.ptit.sqa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.*;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.service.EmailTemplateService;
import vn.edu.ptit.sqa.service.PriceListService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = {ConfigSecurity.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmailTemplateController.class)
public class EmailTemplateControllerTest {

    @MockBean
    private EmailTemplateService emailTemplateService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetAllPriceList() throws Exception {
        TemplateResponse templateResponse = new TemplateResponse();
        templateResponse.setId(8);
        templateResponse.setTemplateName("Thư thông báo tiền nước");
        templateResponse.setTemplateSubject("Thông báo tiền nước");
        templateResponse.setTemplateContent("<div><div></div><div><p>Xin chào Quý khách hàng {{customerName}},</p><p>Công ty nước sạch Hà Đông xin gửi đến Quý khách hàng thông tin về số tiền nước tháng vừa qua:</p><table><tr><td><strong>Kỳ thanh toán:</strong></td><td>Tháng {{month}}</td></tr><tr><td><strong>Số nước đã sử dụng:</strong></td><td>{{amountOfWater}} mét khối</td></tr><tr><td><strong>Tổng cộng thanh toán:</strong></td><td>{{total}} VND</td></tr></table><i>(Tổng tiền thanh toán đã bao gồm thuế suất GTGT 5% và phí BVMT 10%)</p><p>Vui lòng thanh toán số tiền trên trước ngày hết hạn để tránh phát sinh các khoản phí trễ hạn.</p><p>Cảm ơn Quý khách hàng đã sử dụng dịch vụ của chúng tôi.</p><div><p><strong>Công ty nước sạch Hà Đông</strong></p><p><strong>Địa chỉ:</strong> 9 Trần Phú, Hà Đông, Hà Nội</p><p><strong>Số điện thoại:</strong> 09884399930</p><p><strong>Mã số thuế:</strong> 1809043392</p></div></div><div><p>Email này được gửi tự động. Vui lòng không trả lời.</p></div></div>");
        templateResponse.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        TemplateResponse templateResponse2 = new TemplateResponse();
        templateResponse2.setId(10);
        templateResponse2.setTemplateName("Thư thông báo nợ tiền nước");
        templateResponse2.setTemplateSubject("Thông báo nợ tiền nước");
        templateResponse2.setTemplateContent("<div><div></div><div><p>Xin chào Quý khách hàng {{customerName}},</p><p>Là khách hàng đang sử dụng nước sạch của Công ty nước sạch Hà Đông.</td><td>Qua kiểm tra thông tin trên hệ thống, đến nay quý khách hàng vẫn chưa thanh toán tiền nước <strong>Tháng {{month}}</strong> </td><td> Số nước tiêu thụ: {{amountOfWater}} mét khối</td></tr><tr><td><strong>Tổng cộng thanh toán:</strong></td><td>{{total}} VND</td></tr></table><i>(Tổng tiền thanh toán đã bao gồm thuế suất GTGT 5% và phí BVMT 10%)</p><p>Vậy kính đề nghị quý khách hàng kịp thời thanh toán số tiền trên trước <strong>{{dueDate}}.</strong></p><p>Nếu quá thời hạn trên khách hàng không thanh toán tiền nước, chúng tôi sẽ làm thủ tục xử lý theo quy định hiện hành.</p><div><p><strong>Công ty nước sạch Hà Đông</strong></p><p><strong>Địa chỉ:</strong> 9 Trần Phú, Hà Đông, Hà Nội</p><p><strong>Số điện thoại:</strong> 09884399930</p><p><strong>Mã số thuế:</strong> 1809043392</p></div></div><div><p>Email này được gửi tự động. Vui lòng không trả lời.</p></div></div>");
        templateResponse2.setCreatedDate(new Timestamp(System.currentTimeMillis()));


        List<TemplateResponse> templateResponses = new ArrayList<>();
        templateResponses.add(templateResponse);
        templateResponses.add(templateResponse2);
        DataTableResults<TemplateResponse> dataTableResults = new DataTableResults<>();
        dataTableResults.setCurrentPage(0);
        dataTableResults.setTotalItems(2L);
        dataTableResults.setTotalPages(1);
        dataTableResults.setData(templateResponses);

        PaginationRequest paginationRequest = new PaginationRequest(true, 0, 2);
        when(emailTemplateService.getAllEmailTemplates(paginationRequest))
                .thenReturn(dataTableResults);
        mockMvc.perform(get("/api/emailTemplate?pageNum=0&pageSize=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
