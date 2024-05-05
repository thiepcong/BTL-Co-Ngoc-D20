package vn.edu.ptit.sqa.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.edu.ptit.sqa.entity.EmailAttachment;
import vn.edu.ptit.sqa.entity.EmailDetail;
import vn.edu.ptit.sqa.entity.EmailTemplate;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.EmailDetailAM;
import vn.edu.ptit.sqa.model.EmailDetailDto;
import vn.edu.ptit.sqa.model.reportInfor.*;
import vn.edu.ptit.sqa.repository.EmailAttachmentRepo;
import vn.edu.ptit.sqa.repository.EmailDetailRepo;
import vn.edu.ptit.sqa.service.CustomerInforService;
import vn.edu.ptit.sqa.service.EmailTemplateService;
import vn.edu.ptit.sqa.service.FTPService;

@Nested
@ContextConfiguration(classes = {EmailDetailServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EmailDetailServiceTest {
  @MockBean
  private CustomerInforService customerInforService;

  @MockBean
  private EmailAttachmentRepo emailAttachmentRepo;

  @MockBean
  private EmailDetailRepo emailDetailRepo;

  @Autowired
  private EmailDetailServiceImpl emailDetailServiceImpl;

  @MockBean
  private EmailTemplateService emailTemplateService;

  @MockBean
  private FTPService fTPService;

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createEmailDetail(EmailDetailAM)}
   */
  @Test
  void testCreateEmailDetail_invalidTemplateId() {
    // Arrange
    EmailTemplate emailTemplate = new EmailTemplate();
    emailTemplate.setId(1);
    when(emailTemplateService.getEmailTemplateById(Mockito.<Integer>any())).thenReturn(emailTemplate);

    ReportInforRequest reportInforRequest = new ReportInforRequest();
    reportInforRequest.setDistrict("District");
    reportInforRequest.setInvoiceStatus("Invoice Status");
    reportInforRequest.setMonth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    reportInforRequest.setPage(1);
    reportInforRequest.setProvine("Provine");
    reportInforRequest.setSearch("Search");
    reportInforRequest.setSize(3);
    reportInforRequest.setWard("Ward");

    EmailDetailAM request = new EmailDetailAM();
    request.setAttachmentIds(new ArrayList<>());
    request.setReportInforRequest(reportInforRequest);
    request.setTemplateId(1);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> emailDetailServiceImpl.createEmailDetail(request));
    verify(emailTemplateService).getEmailTemplateById(eq(1));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createEmailDetail(EmailDetailAM)}
   */
  @Test
  void testCreateEmailDetail_withValidTemplateId_Equal_8() {
    // Arrange
    ReportInforResponse reportInforResponse = new ReportInforResponse();
    reportInforResponse.setReportDTOList(new ArrayList<>());
    when(customerInforService.getUnPaidClientList(Mockito.<ReportInforRequest>any(), Mockito.<Pageable>any()))
            .thenReturn(reportInforResponse);

    EmailTemplate emailTemplate = new EmailTemplate();
    emailTemplate.setCreatedDate(mock(Timestamp.class));
    emailTemplate.setId(8);
    emailTemplate.setListEmailDetails(new ArrayList<>());
    emailTemplate.setTemplateContent("Chào quý khách hàng, ...");
    emailTemplate.setTemplateName("Thư thông báo tiền nước");
    emailTemplate.setTemplateSubject("Thư thông bao tien nuoc thang 4");
    when(emailTemplateService.getEmailTemplateById(Mockito.<Integer>any())).thenReturn(emailTemplate);

    ReportInforRequest reportInforRequest = new ReportInforRequest();
    reportInforRequest.setDistrict("District");
    reportInforRequest.setInvoiceStatus("Invoice Status");
    reportInforRequest.setMonth(Date.from(LocalDate.of(2024, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    reportInforRequest.setPage(1);
    reportInforRequest.setProvine("Provine");
    reportInforRequest.setSearch("Search");
    reportInforRequest.setSize(3);
    reportInforRequest.setWard("Ward");

    EmailDetailAM request = new EmailDetailAM();
    request.setAttachmentIds(new ArrayList<>());
    request.setReportInforRequest(reportInforRequest);
    request.setTemplateId(8);

    // Act
    Boolean actualCreateEmailDetailResult = emailDetailServiceImpl.createEmailDetail(request);

    // Assert
    verify(customerInforService).getUnPaidClientList(isA(ReportInforRequest.class), isNull());
    verify(emailTemplateService).getEmailTemplateById(eq(8));
    assertTrue(actualCreateEmailDetailResult);
  }


  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createEmailDetail(EmailDetailAM)}
   */
  @Test
  void testCreateEmailDetail_withValidTemplateId_Equal_10() {
    // Arrange
    DebtReportResponse reportInforResponse = new DebtReportResponse();
    reportInforResponse.setDebtReportDTO(new DebtReportDTO());
    reportInforResponse.setDebtCustomerList(new ArrayList<>());
    when(customerInforService.getDebtCustomerList(Mockito.<ReportInforRequest>any(), Mockito.<Pageable>any()))
            .thenReturn(reportInforResponse);

    EmailTemplate emailTemplate = new EmailTemplate();
    emailTemplate.setCreatedDate(mock(Timestamp.class));
    emailTemplate.setId(10);
    emailTemplate.setListEmailDetails(new ArrayList<>());
    emailTemplate.setTemplateContent("Chào quý khách hàng, ...");
    emailTemplate.setTemplateName("Thư thông báo nợ tiền nước");
    emailTemplate.setTemplateSubject("Thư thông bao no tien nuoc thang 4");
    when(emailTemplateService.getEmailTemplateById(Mockito.<Integer>any())).thenReturn(emailTemplate);

    ReportInforRequest reportInforRequest = new ReportInforRequest();
    reportInforRequest.setDistrict("District");
    reportInforRequest.setInvoiceStatus("Invoice Status");
    reportInforRequest.setMonth(Date.from(LocalDate.of(2024, 3, 28).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    reportInforRequest.setPage(1);
    reportInforRequest.setProvine("Provine");
    reportInforRequest.setSearch("Search");
    reportInforRequest.setSize(3);
    reportInforRequest.setWard("Ward");

    EmailDetailAM request = new EmailDetailAM();
    request.setAttachmentIds(new ArrayList<>());
    request.setReportInforRequest(reportInforRequest);
    request.setTemplateId(10);

    // Act
    Boolean actualCreateEmailDetailResult = emailDetailServiceImpl.createEmailDetail(request);

    // Assert
    verify(customerInforService).getDebtCustomerList(isA(ReportInforRequest.class), isNull());
    verify(emailTemplateService).getEmailTemplateById(eq(10));
    assertTrue(actualCreateEmailDetailResult);
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createEmailDetail(EmailDetailAM)}
   */
  @Test
  void testCreateEmailDetail_withAttachmentNotFound() {
    // Arrange
    when(emailAttachmentRepo.getById(Mockito.<Long>any())).thenThrow(new NotFoundException("Attachment not found"));

    EmailTemplate emailTemplate = new EmailTemplate();
    emailTemplate.setCreatedDate(mock(Timestamp.class));
    emailTemplate.setId(8);
    emailTemplate.setListEmailDetails(new ArrayList<>());
    emailTemplate.setTemplateContent("Not all who wander are lost");
    emailTemplate.setTemplateName("Template Name");
    emailTemplate.setTemplateSubject("Hello from the Dreaming Spires");
    when(emailTemplateService.getEmailTemplateById(Mockito.<Integer>any())).thenReturn(emailTemplate);

    ArrayList<Long> attachmentIds = new ArrayList<>();
    attachmentIds.add(99L);

    ReportInforRequest reportInforRequest = new ReportInforRequest();
    reportInforRequest.setDistrict("District");
    reportInforRequest.setInvoiceStatus("Invoice Status");
    reportInforRequest.setMonth(Date.from(LocalDate.of(2024, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    reportInforRequest.setPage(1);
    reportInforRequest.setProvine("Provine");
    reportInforRequest.setSearch("Search");
    reportInforRequest.setSize(3);
    reportInforRequest.setWard("Ward");

    EmailDetailAM request = new EmailDetailAM();
    request.setAttachmentIds(attachmentIds);
    request.setReportInforRequest(reportInforRequest);
    request.setTemplateId(1);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> emailDetailServiceImpl.createEmailDetail(request));
    verify(emailAttachmentRepo).getById(eq(99L));
    verify(emailTemplateService).getEmailTemplateById(eq(1));
  }


//  =============================================================
//  Junit test CreateUnpaidCustomerContext()

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createUnpaidCustomerContext(String, ReportDTO)}
   */
  @Test
  void testCreateUnpaidCustomerContext_NotHaveTemplateProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ReportDTO reportDTO = new ReportDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine",
            "District", "Ward", 1L, 1L, startTime,
            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status");
    reportDTO.setNewWaterUsageIndex(7L);

    // Act and Assert
    assertEquals("Template Detail", emailDetailServiceImpl.createUnpaidCustomerContext("Template Detail", reportDTO));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createUnpaidCustomerContext(String, ReportDTO)}
   */
  @Test
  void testCreateUnpaidCustomerContext_HaveCustomerNameProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ReportDTO reportDTO = new ReportDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine",
            "District", "Ward", 1L, 1L, startTime,
            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status");
    reportDTO.setNewWaterUsageIndex(7L);

    // Act and Assert
    assertEquals("Customer Name", emailDetailServiceImpl.createUnpaidCustomerContext("{{customerName}}", reportDTO));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createUnpaidCustomerContext(String, ReportDTO)}
   */
  @Test
  void testCreateUnpaidCustomerContext_HaveAmountOfWaterProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ReportDTO reportDTO = new ReportDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine",
            "District", "Ward", 1L, 1L, startTime,
            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status");
    reportDTO.setNewWaterUsageIndex(7L);

    // Act and Assert
    assertEquals("6", emailDetailServiceImpl.createUnpaidCustomerContext("{{amountOfWater}}", reportDTO));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createUnpaidCustomerContext(String, ReportDTO)}
   */
  @Test
  void testCreateUnpaidCustomerContext_HaveTotalMoneyProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ReportDTO reportDTO = new ReportDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine",
            "District", "Ward", 1L, 1L, startTime,
            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status");
    reportDTO.setNewWaterUsageIndex(7L);

    // Act and Assert
    assertEquals("null", emailDetailServiceImpl.createUnpaidCustomerContext("{{total}}", reportDTO));
  }

  // =============================================================
  // Junit test CreateDebtCustomerContext()

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createDebtCustomerContext(String, DebtCustomerDTO)}
   */
  @Test
  void testCreateDebtCustomerContext_NotHaveProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals("Template Detail",
            emailDetailServiceImpl.createDebtCustomerContext("Template Detail",
                    new DebtCustomerDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine", "District",
                            "Ward", 1L, 1L, startTime,
                            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
                            "debt")));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createDebtCustomerContext(String, DebtCustomerDTO)}
   */
  @Test
  void testCreateDebtCustomerContext_HaveCustomerNameProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals("Customer Name",
            emailDetailServiceImpl.createDebtCustomerContext("{{customerName}}",
                    new DebtCustomerDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine", "District",
                            "Ward", 1L, 1L, startTime,
                            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
                            "debt")));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createDebtCustomerContext(String, DebtCustomerDTO)}
   */
  @Test
  void testCreateDebtCustomerContext_HaveAmountOfWaterProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals("0", emailDetailServiceImpl.createDebtCustomerContext("{{amountOfWater}}", new DebtCustomerDTO(1L,
            "Customer Name", "6625550144", "jane.doe@example.org", "Provine", "District", "Ward", 1L, 1L, startTime,
            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status")));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createDebtCustomerContext(String, DebtCustomerDTO)}
   */
  @Test
  void testCreateDebtCustomerContext_HaveTotalMoneyProperty() {
    // Arrange
    java.util.Date startTime = java.util.Date
            .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals("0.0", emailDetailServiceImpl.createDebtCustomerContext("{{total}}", new DebtCustomerDTO(1L,
            "Customer Name", "6625550144", "jane.doe@example.org", "Provine", "District", "Ward", 1L, 1L, startTime,
            java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status")));
  }


  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createDebtCustomerContext(String, DebtCustomerDTO)}
   */
  @Test
  void testCreateDebtCustomerContext_HaveDueDateProperty() {
    // Arrange
    java.sql.Date startTime = mock(java.sql.Date.class);
    when(startTime.getTime()).thenReturn(10L);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startTime);

    calendar.add(Calendar.DAY_OF_MONTH, 3);
    Date resultDateTime = calendar.getTime();
    // Act
    String actualCreateDebtCustomerContextResult = emailDetailServiceImpl.createDebtCustomerContext("{{dueDate}}",
            new DebtCustomerDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine", "District", "Ward",
                    1L, 1L, startTime,
                    java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status"));

    // Assert
    verify(startTime, Mockito.times(2)).getTime();
    assertEquals(String.valueOf(resultDateTime), actualCreateDebtCustomerContextResult);
  }


  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createDebtCustomerContext(String, DebtCustomerDTO)}
   */
  @Test
  void testCreateDebtCustomerContext_HaveMonthProperty() {
    // Arrange
    java.sql.Date startTime = mock(java.sql.Date.class);
    when(startTime.getTime()).thenReturn(10L);

    // Act
    String actualCreateDebtCustomerContextResult = emailDetailServiceImpl.createDebtCustomerContext("{{month}}",
            new DebtCustomerDTO(1L, "Customer Name", "6625550144", "jane.doe@example.org", "Provine", "District", "Ward",
                    1L, 1L, startTime,
                    java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Status"));

    // Assert
    verify(startTime).getTime();
    assertEquals("fo - Mock", actualCreateDebtCustomerContextResult);
  }
//  =============================================================
//  Test sendEmail()

  /**
   * Method under test: {@link EmailDetailServiceImpl#sendEmail(EmailDetailDto)}
   */
  @Test
  void testSendEmail() {
    // Arrange
    EmailTemplate emailTemplate = new EmailTemplate();
    emailTemplate.setCreatedDate(mock(Timestamp.class));
    emailTemplate.setId(8);
    emailTemplate.setListEmailDetails(new ArrayList<>());
    emailTemplate.setTemplateContent("Chao quy khach hang {{customName}}, ...");
    emailTemplate.setTemplateName("Thu thong bao tien nuoc");
    emailTemplate.setTemplateSubject("Thông báo tiền nước tháng 4");

    User user = new User();
    user.setAddress("217 Trần Phú");
    user.setCreatAt(mock(Timestamp.class));
    user.setDateOfBirth(mock(java.sql.Date.class));
    user.setEmail("huynhnguyenadmin@gmail.com");
    user.setId(1);
    user.setInvoices(new ArrayList<>());
    user.setListEmailDetails(new HashSet<>());
    user.setName("Huynh");
    user.setPassword("123123");
    user.setPhoneNumber("6625550144");
    user.setPriceListsCreated(new ArrayList<>());
    user.setRoles(new HashSet<>());
    user.setUsername("huynhnguyen");

    EmailDetail emailDetail = new EmailDetail();
    emailDetail.setContext("Chào quý khách hàng Cảnh Huỳnh, ...");
    emailDetail.setCreatedDate(mock(Timestamp.class));
    emailDetail.setEmailAttachments(new ArrayList<>());
    emailDetail.setEmailSender("httinhtiennuoc@gmail.com");
    emailDetail.setEmailTemplate(emailTemplate);

    emailDetail.setStatus(1);
    emailDetail.setSubject("Thư thông báo tiền nước tháng 4");
    emailDetail.setToEmail("canhhuynh@gmail.com");
    emailDetail.setUser(user);
    when(emailDetailRepo.getById(Mockito.<Long>any())).thenReturn(emailDetail);
    emailDetail.setId(1L);
    when(emailDetailRepo.save(Mockito.<EmailDetail>any())).thenReturn(emailDetail);

    EmailDetailDto request = new EmailDetailDto();
    emailDetail.setContext("Chào quý khách hàng Cảnh Huỳnh, ...");
    request.setCreatedDate(mock(Timestamp.class));
    request.setEmailAttachments(new ArrayList<>());
    request.setEmailSender("httinhtiennuoc@gmail.com");

    request.setStatus(1);
    request.setSubject("Thư thông báo tiền nước tháng 4");
    request.setToEmail("canhhuynh@gmail.com");
    request.setId(1L);
    HashMap<String, byte[]> attachmentFiles = new HashMap<>();
//    EmailUtil emailUtilMock = mock(EmailUtil.class);
//    EmailUtil.sendEmailWithAttachments(request.getToEmail(),
//            request.getSubject(),
//            request.getContext(), attachmentFiles);
    // Act
    Boolean actualSendEmailResult = emailDetailServiceImpl.sendEmail(request);

    // Assert
    verify(emailDetailRepo).getById(1L);
    verify(emailDetailRepo).save(isA(EmailDetail.class));
//    verify(emailUtilMock).sendEmailWithAttachments(request.getToEmail(),
//            request.getSubject(),
//            request.getContext(), attachmentFiles);
    assertEquals(-1, request.getStatus().intValue());
    assertFalse(actualSendEmailResult);
  }
}