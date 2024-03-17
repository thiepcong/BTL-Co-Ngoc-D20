package vn.edu.ptit.sqa.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.config.AppProperties;
import vn.edu.ptit.sqa.entity.EmailAttachment;
import vn.edu.ptit.sqa.entity.EmailDetail;
import vn.edu.ptit.sqa.entity.EmailTemplate;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.AllUserFilter;
import vn.edu.ptit.sqa.model.EmailAttachmentResponse;
import vn.edu.ptit.sqa.model.EmailDetailAM;
import vn.edu.ptit.sqa.model.EmailDetailDto;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.EmailDetailRepo;
import vn.edu.ptit.sqa.service.EmailDetailService;
import vn.edu.ptit.sqa.service.EmailTemplateService;
import vn.edu.ptit.sqa.service.FTPService;
import vn.edu.ptit.sqa.util.EmailUtil;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailDetailServiceImpl implements EmailDetailService {

    @Autowired
    EmailTemplateService emailTemplateService;

    @Autowired
    FTPService ftpService;

    @Autowired
    EmailDetailRepo emailDetailRepo;
    @Transactional
    @Override
    public void createEmailDetail(EmailDetailAM request, AllUserFilter allUserFilter ) {
        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplateById(request.getTemplate().getId());
        if(!allUserFilter.getIsFilterAll()) {
            for (int i = 0; i < request.getToList().size(); i++) {
                String to = request.getToList().get(i);
                EmailDetail emailDetail = new EmailDetail();
                emailDetail.setEmailTemplate(emailTemplate);
                emailDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                emailDetail.setStatus(AppProperties.EMAIL_DETAIL_STATUS.INIT);
                emailDetail.setToEmail(to);
                emailDetail.setEmailAttachments(request.getAttachments().stream()
                        .map(emailAttachment -> ConverterUtil.mappingToObject(emailAttachment, EmailAttachment.class))
                        .collect(Collectors.toList()));
                emailDetail.setSubject(request.getTemplate().getTemplateSubject());
                emailDetail.setContext(createContext(emailTemplate));

                emailDetail.setEmailSender(AppProperties.EMAIL_SERVER.USERNAME);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof User) {
                        emailDetail.setUser((User) principal);
                    }
                }
                emailDetail = emailDetailRepo.save(emailDetail);

                EmailDetailDto emailDetailMessage = ConverterUtil.mappingToObject(emailDetail, EmailDetailDto.class);
                emailDetailMessage.setEmailAttachments(ConverterUtil.mapList(emailDetail.getEmailAttachments().stream().toList(), EmailAttachmentResponse.class));
                sendEmail(emailDetailMessage);
            }
        }else{

        }
    }

    public String createContext(EmailTemplate emailTemplate){
        String templateDetail = emailTemplate.getTemplateContent();
        Pattern pattern = Pattern.compile(AppProperties.EMAIL_PROPERTY.PATTERN_PROPERTY);
        Matcher matcher = pattern.matcher(templateDetail);

        while (matcher.find()) {
            log.info(matcher.group());
            templateDetail = templateDetail.replaceAll(matcher.group(), "Huynh");
        }

        return templateDetail;
    }

    @Override
    @Transactional
    public void sendEmail(EmailDetailDto request) {
        try{

            HashMap<String, byte[]> attachmentFiles = new HashMap<>();
            request.getEmailAttachments().forEach(attachment ->{

                int lastSlash = attachment.getFileName().lastIndexOf('/');

                attachmentFiles.put(attachment.getOriginalFileName(),
                        ftpService.readFile(AppProperties.EMAIL_ATTACHMENT.REMOTE_FILE_PATH +
                                attachment.getFileName().substring(lastSlash + 1)));

            });

            EmailUtil.sendEmailWithAttachments(
                    request.getToEmail(),
                    request.getSubject(),
                    request.getContext(),
                    attachmentFiles);

            request.setStatus(AppProperties.EMAIL_DETAIL_STATUS.SEND_SUCCESS);
            log.info("Send email successfully!");
        }catch(Exception e){
            request.setStatus(AppProperties.EMAIL_DETAIL_STATUS.SEND_FAIL);

            log.warn("Send email failed " + request.getToEmail());
            log.warn(e.getMessage());
        }
        EmailDetail emailDetail = emailDetailRepo.getById(request.getId());
        emailDetail.setStatus(request.getStatus());
        emailDetailRepo.save(emailDetail);

    }

    @Override
    public EmailDetailDto getEmailDetailById(Long id) {
        EmailDetail emailDetail = emailDetailRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("EmailDetail id " + id));
        return ConverterUtil.mappingToObject(emailDetail, EmailDetailDto.class);
    }

    @Override
    public DataTableResults<EmailDetailDto> getAllEmailDetails(PaginationRequest paginationRequest){
        Pageable paging = PageRequest.of(paginationRequest.getPageNum()-1, paginationRequest.getPageSize());

        Page<EmailDetail> emailDetails = emailDetailRepo.findAll(paging);
        List<EmailDetail> emailDetailList = emailDetails.getContent();

        List<EmailDetailDto> emailDetailDtos = ConverterUtil.mapList(emailDetailList, EmailDetailDto.class);
        DataTableResults<EmailDetailDto> res = new DataTableResults<>(emailDetailDtos);
        res.setTotalPages(emailDetails.getTotalPages());
        res.setTotalItems(emailDetails.getTotalElements());
        res.setCurrentPage(emailDetails.getNumber()+1);

        return res;
    }
}
