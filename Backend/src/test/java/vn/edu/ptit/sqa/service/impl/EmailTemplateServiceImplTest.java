package vn.edu.ptit.sqa.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.edu.ptit.sqa.entity.EmailTemplate;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.TemplateResponse;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.EmailTemplateRepo;
import vn.edu.ptit.sqa.service.EmailTemplateService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EmailTemplateServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class EmailTemplateServiceImplTest {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @MockBean
    private EmailTemplateRepo emailTemplateRepo;

    @Test
    public void testGetEmailTemplateById_NotFound() {
        when(emailTemplateRepo.findById(100))
                .thenThrow(new NotFoundException("Email Template 100 not found"));
        assertThrows(NotFoundException.class, () -> emailTemplateService.getEmailTemplateById(100));

    }

    @Test
    public void testGetEmailTemplateById_Valid() {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(8);
        emailTemplate.setTemplateName("Thư thông báo tiền nước");
        emailTemplate.setTemplateSubject("Thông báo tiền nước");
        emailTemplate.setTemplateContent("<div><div></div><div><p>Xin chào Quý khách hàng {{customerName}},</p><p>Công ty nước sạch Hà Đông xin gửi đến Quý khách hàng thông tin về số tiền nước tháng vừa qua:</p><table><tr><td><strong>Kỳ thanh toán:</strong></td><td>Tháng {{month}}</td></tr><tr><td><strong>Số nước đã sử dụng:</strong></td><td>{{amountOfWater}} mét khối</td></tr><tr><td><strong>Tổng cộng thanh toán:</strong></td><td>{{total}} VND</td></tr></table><i>(Tổng tiền thanh toán đã bao gồm thuế suất GTGT 5% và phí BVMT 10%)</p><p>Vui lòng thanh toán số tiền trên trước ngày hết hạn để tránh phát sinh các khoản phí trễ hạn.</p><p>Cảm ơn Quý khách hàng đã sử dụng dịch vụ của chúng tôi.</p><div><p><strong>Công ty nước sạch Hà Đông</strong></p><p><strong>Địa chỉ:</strong> 9 Trần Phú, Hà Đông, Hà Nội</p><p><strong>Số điện thoại:</strong> 09884399930</p><p><strong>Mã số thuế:</strong> 1809043392</p></div></div><div><p>Email này được gửi tự động. Vui lòng không trả lời.</p></div></div>");
        emailTemplate.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        emailTemplate.setListEmailDetails(null);

        when(emailTemplateRepo.findById(8)).thenReturn(Optional.of(emailTemplate));
        assertEquals(emailTemplate, emailTemplateService.getEmailTemplateById(8));

    }

    @Test
    public void getAllEmailTemplates_Valid() {
        EmailTemplate emailTemplate = new EmailTemplate();

//        TemplateResponse templateResponse = new TemplateResponse();
        emailTemplate.setId(8);
        emailTemplate.setTemplateName("Thư thông báo tiền nước");
        emailTemplate.setTemplateSubject("Thông báo tiền nước");
        emailTemplate.setTemplateContent("<div><div></div><div><p>Xin chào Quý khách hàng {{customerName}},</p><p>Công ty nước sạch Hà Đông xin gửi đến Quý khách hàng thông tin về số tiền nước tháng vừa qua:</p><table><tr><td><strong>Kỳ thanh toán:</strong></td><td>Tháng {{month}}</td></tr><tr><td><strong>Số nước đã sử dụng:</strong></td><td>{{amountOfWater}} mét khối</td></tr><tr><td><strong>Tổng cộng thanh toán:</strong></td><td>{{total}} VND</td></tr></table><i>(Tổng tiền thanh toán đã bao gồm thuế suất GTGT 5% và phí BVMT 10%)</p><p>Vui lòng thanh toán số tiền trên trước ngày hết hạn để tránh phát sinh các khoản phí trễ hạn.</p><p>Cảm ơn Quý khách hàng đã sử dụng dịch vụ của chúng tôi.</p><div><p><strong>Công ty nước sạch Hà Đông</strong></p><p><strong>Địa chỉ:</strong> 9 Trần Phú, Hà Đông, Hà Nội</p><p><strong>Số điện thoại:</strong> 09884399930</p><p><strong>Mã số thuế:</strong> 1809043392</p></div></div><div><p>Email này được gửi tự động. Vui lòng không trả lời.</p></div></div>");
        emailTemplate.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        EmailTemplate emailTemplate2 = new EmailTemplate();
//        TemplateResponse templateResponse2 = new TemplateResponse();
        emailTemplate2.setId(10);
        emailTemplate2.setTemplateName("Thư thông báo nợ tiền nước");
        emailTemplate2.setTemplateSubject("Thông báo nợ tiền nước");
        emailTemplate2.setTemplateContent("<div><div></div><div><p>Xin chào Quý khách hàng {{customerName}},</p><p>Là khách hàng đang sử dụng nước sạch của Công ty nước sạch Hà Đông.</td><td>Qua kiểm tra thông tin trên hệ thống, đến nay quý khách hàng vẫn chưa thanh toán tiền nước <strong>Tháng {{month}}</strong> </td><td> Số nước tiêu thụ: {{amountOfWater}} mét khối</td></tr><tr><td><strong>Tổng cộng thanh toán:</strong></td><td>{{total}} VND</td></tr></table><i>(Tổng tiền thanh toán đã bao gồm thuế suất GTGT 5% và phí BVMT 10%)</p><p>Vậy kính đề nghị quý khách hàng kịp thời thanh toán số tiền trên trước <strong>{{dueDate}}.</strong></p><p>Nếu quá thời hạn trên khách hàng không thanh toán tiền nước, chúng tôi sẽ làm thủ tục xử lý theo quy định hiện hành.</p><div><p><strong>Công ty nước sạch Hà Đông</strong></p><p><strong>Địa chỉ:</strong> 9 Trần Phú, Hà Đông, Hà Nội</p><p><strong>Số điện thoại:</strong> 09884399930</p><p><strong>Mã số thuế:</strong> 1809043392</p></div></div><div><p>Email này được gửi tự động. Vui lòng không trả lời.</p></div></div>");
        emailTemplate2.setCreatedDate(new Timestamp(System.currentTimeMillis()));


        List<EmailTemplate> emailTemplates = new ArrayList<>();
        emailTemplates.add(emailTemplate);
        emailTemplates.add(emailTemplate2);



        Page<EmailTemplate> page = new PageImpl<>(emailTemplates, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id")), 2);
        when(emailTemplateRepo.findAll(any(Pageable.class))).thenReturn(page);

        DataTableResults<TemplateResponse> dataTableResults = new DataTableResults<>();
        dataTableResults.setCurrentPage(0);
        dataTableResults.setTotalItems(2L);
        dataTableResults.setTotalPages(1);
        dataTableResults.setData(ConverterUtil.mapList(emailTemplates, TemplateResponse.class));
        PaginationRequest paginationRequest = new PaginationRequest(true, 0, 2);
        DataTableResults<TemplateResponse> result = emailTemplateService.getAllEmailTemplates(paginationRequest);
        assertEquals(dataTableResults.getData().get(0).getTemplateName(), result.getData().get(0).getTemplateName());
        assertEquals(dataTableResults.getData().get(1).getTemplateName(), result.getData().get(1).getTemplateName());

    }
}
