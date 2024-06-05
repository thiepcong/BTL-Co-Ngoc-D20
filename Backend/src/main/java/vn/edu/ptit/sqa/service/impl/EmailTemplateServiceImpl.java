package vn.edu.ptit.sqa.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.entity.EmailTemplate;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.TemplateAM;
import vn.edu.ptit.sqa.model.TemplateResponse;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.EmailTemplateRepo;
import vn.edu.ptit.sqa.service.EmailTemplateService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepo emailTemplateRepo;

//    @Override
//    @Transactional
//    public TemplateResponse createTemplate(TemplateAM templateAM) {
//        EmailTemplate emailTemplate = ConverterUtil.mappingToObject(templateAM, EmailTemplate.class);
//        emailTemplate.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//        emailTemplate = emailTemplateRepo.save(emailTemplate);
//        return ConverterUtil.mappingToObject(emailTemplate, TemplateResponse.class);
//    }

//    @Override
//    public TemplateResponse getEmailTemplateResById(Integer id) {
//        Optional<EmailTemplate> emailTemplate = emailTemplateRepo.findById(id);
//        if(emailTemplate.isEmpty()) throw new NotFoundException("Email Template " + id + " not found");
//        TemplateResponse templateResponse = ConverterUtil.mappingToObject(emailTemplate.get(), TemplateResponse.class);
//        return templateResponse;
//    }

    @Override
    public EmailTemplate getEmailTemplateById(Integer id) {
        return emailTemplateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Email Template " + id + " not found"));
    }

    @Override
    public DataTableResults<TemplateResponse> getAllEmailTemplates(PaginationRequest paginationRequest) {
        Pageable paging = PageRequest.of(paginationRequest.getPageNum()-1, paginationRequest.getPageSize());

        Page<EmailTemplate> emailTemplates = emailTemplateRepo.findAll(paging);
        List<EmailTemplate> emailTemplatesList = emailTemplates.getContent();

        List<TemplateResponse> emailTemplatesResponse = ConverterUtil.mapList(emailTemplatesList, TemplateResponse.class);
        DataTableResults<TemplateResponse> res = new DataTableResults<>(emailTemplatesResponse);
        res.setTotalPages(emailTemplates.getTotalPages());
        res.setTotalItems(emailTemplates.getTotalElements());
        res.setCurrentPage(emailTemplates.getNumber()+1);

        return res;
    }
}
