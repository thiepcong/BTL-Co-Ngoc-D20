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
import vn.edu.ptit.sqa.util.EmailUtil;

@Nested
@ContextConfiguration(classes = {EmailDetailServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EmailDetailServiceImplTest {
  @MockBean
  private CustomerInforService customerInforService;

  @MockBean
  private EmailDetailRepo emailDetailRepo;

  @Autowired
  private EmailDetailServiceImpl emailDetailServiceImpl;

  @MockBean
  private EmailAttachmentRepo emailAttachmentRepo;
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

    ReportDTO reportDTO = new ReportDTO();
    reportDTO.setCustomerName("HuynhNC");
    reportDTO.setCustomerEmail("huynhnguyen@gmail.com");
    reportDTO.setCustomerPhone("0123456789");
    reportDTO.setWard("Văn Quán");
    reportDTO.setProvine("Hà Nội");
    reportDTO.setDistrict("Hà Đông");
    reportDTO.setOldWaterUsageIndex(157L);
    reportDTO.setNewWaterUsageIndex(200L);
    reportDTO.setMoneyPrice(216000);
    reportDTO.setStartTime(new Date());
    reportDTO.setEndTime(new Date());
    reportDTO.setStatus("unpaid");
    reportDTO.setTotaMoney(216000L);
    reportDTO.setWaterUsageNumber(43L);
    reportDTO.setCustomerId(1L);
    List<ReportDTO> reportDTOList = new ArrayList<>();
    reportDTOList.add(reportDTO);
    reportInforResponse.setReportDTOList(reportDTOList);
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
    EmailDetail emailDetail = new EmailDetail();
    emailDetail.setToEmail("huynhnguyen@gmail.com");
    emailDetail.setEmailSender("huynhnc@gmail.com");
    emailDetail.setEmailTemplate(emailTemplate);
    emailDetail.setContext("Chào quý khách hàng, ...");
    emailDetail.setSubject("Thư thông bao tien nuoc thang 4");
    emailDetail.setStatus(1);
    emailDetail.setEmailAttachments(new ArrayList<>());
    when(emailDetailRepo.save(Mockito.<EmailDetail>any())).thenReturn(emailDetail);
    when(emailDetailRepo.getById(Mockito.<Long>any())).thenReturn(emailDetail);
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
//    verify(customerInforService).getUnPaidClientList(isA(ReportInforRequest.class), isNull());
//    verify(emailTemplateService).getEmailTemplateById(eq(8));
    assertTrue(actualCreateEmailDetailResult);
  }

  @Test
  void testCreateEmailDetail_withValidTemplateId_NotFound() {

    when(emailTemplateService.getEmailTemplateById(Mockito.<Integer>any())).thenThrow(new NotFoundException("Send type not found"));

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
    request.setTemplateId(5);

    assertThrows(NotFoundException.class, () -> emailDetailServiceImpl.createEmailDetail(request));
    verify(emailTemplateService).getEmailTemplateById(eq(5));
  }

  /**
   * Method under test:
   * {@link EmailDetailServiceImpl#createEmailDetail(EmailDetailAM)}
   */
  @Test
  void testCreateEmailDetail_withValidTemplateId_Equal_10() {
    // Arrange
    DebtReportResponse reportInforResponse = new DebtReportResponse();
    DebtReportDTO debtReportDTO = new DebtReportDTO();
    debtReportDTO.setDebtNum(50);
    debtReportDTO.setPercent("20%");
    debtReportDTO.setAllCustomerNum(25);
    reportInforResponse.setDebtReportDTO(debtReportDTO);

    DebtCustomerDTO debtCustomerDTO = new DebtCustomerDTO();
    debtCustomerDTO.setCustomerEmail("huynhnguyen@gmail.com");
    debtCustomerDTO.setCustomerName("HuynhNC");
    debtCustomerDTO.setCustomerPhone("0123456789");
    debtCustomerDTO.setCustomerId(2L);
    debtCustomerDTO.setCustomerNumber(5);
    debtCustomerDTO.setEndTime(new Date());
    debtCustomerDTO.setDebtMoneyNumber(1506000L);
    debtCustomerDTO.setStartTime(new Date());
    debtCustomerDTO.setDistrict("Hà Đông");
    debtCustomerDTO.setProvine("Hà Nội");
    debtCustomerDTO.setWard("Văn Quán");
    debtCustomerDTO.setStatus("unpaid");
    debtCustomerDTO.setOldWaterUsageIndex(157L);
    debtCustomerDTO.setNewWaterUsageIndex(200L);

    List<DebtCustomerDTO> debtCustomerDTOList = new ArrayList<>();
    debtCustomerDTOList.add(debtCustomerDTO);
    reportInforResponse.setDebtCustomerList(debtCustomerDTOList);
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
    EmailDetail emailDetail = new EmailDetail();
    emailDetail.setToEmail("huynhnguyen@gmail.com");
    emailDetail.setEmailSender("huynhnc@gmail.com");
    emailDetail.setEmailTemplate(emailTemplate);
    emailDetail.setContext("Chào quý khách hàng, ...");
    emailDetail.setSubject("Thư thông bao tien nuoc thang 4");
    emailDetail.setStatus(1);
    emailDetail.setEmailAttachments(new ArrayList<>());
    when(emailDetailRepo.save(Mockito.<EmailDetail>any())).thenReturn(emailDetail);
    when(emailDetailRepo.getById(Mockito.<Long>any())).thenReturn(emailDetail);
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
//  @Test
//  void testCreateEmailDetail_withAttachmentNotFound() {
//    // Arrange
//    when(emailAttachmentRepo.getById(Mockito.<Long>any())).thenThrow(new NotFoundException("Attachment not found"));
//
//    EmailTemplate emailTemplate = new EmailTemplate();
//    emailTemplate.setCreatedDate(mock(Timestamp.class));
//    emailTemplate.setId(8);
//    emailTemplate.setListEmailDetails(new ArrayList<>());
//    emailTemplate.setTemplateContent("Not all who wander are lost");
//    emailTemplate.setTemplateName("Template Name");
//    emailTemplate.setTemplateSubject("Hello from the Dreaming Spires");
//    when(emailTemplateService.getEmailTemplateById(Mockito.<Integer>any())).thenReturn(emailTemplate);
//
//    ArrayList<Long> attachmentIds = new ArrayList<>();
//    attachmentIds.add(99L);
//
//    ReportInforRequest reportInforRequest = new ReportInforRequest();
//    reportInforRequest.setDistrict("District");
//    reportInforRequest.setInvoiceStatus("Invoice Status");
//    reportInforRequest.setMonth(Date.from(LocalDate.of(2024, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//    reportInforRequest.setPage(1);
//    reportInforRequest.setProvine("Provine");
//    reportInforRequest.setSearch("Search");
//    reportInforRequest.setSize(3);
//    reportInforRequest.setWard("Ward");
//
//    EmailDetailAM request = new EmailDetailAM();
//    request.setAttachmentIds(attachmentIds);
//    request.setReportInforRequest(reportInforRequest);
//    request.setTemplateId(1);
//
//    // Act and Assert
//    assertThrows(NotFoundException.class, () -> emailDetailServiceImpl.createEmailDetail(request));
//    verify(emailAttachmentRepo).getById(eq(99L));
//    verify(emailTemplateService).getEmailTemplateById(eq(1));
//  }


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
  void testSendEmail(){
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
    HashMap<String, byte[]> attachmentFiles = new HashMap<>();
    EmailDetailDto request = new EmailDetailDto();
    emailDetail.setContext("Chào quý khách hàng Cảnh Huỳnh, ...");
    request.setCreatedDate(mock(Timestamp.class));
    request.setEmailAttachments(new ArrayList<>());
    request.setEmailSender("httinhtiennuoc@gmail.com");

    request.setStatus(1);
    request.setSubject("Thư thông báo tiền nước tháng 4");
    request.setToEmail("canhhuynh@gmail.com");
    request.setId(1L);

    // Act
    Boolean actualSendEmailResult = emailDetailServiceImpl.sendEmail(request);

    // Assert
    verify(emailDetailRepo).getById(1L);
    verify(emailDetailRepo).save(isA(EmailDetail.class));

    assertEquals(-1, request.getStatus().intValue());
    assertFalse(actualSendEmailResult);
  }

}