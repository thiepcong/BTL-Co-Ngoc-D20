package vn.edu.ptit.sqa.service;

import vn.edu.ptit.sqa.entity.EmailTemplate;
import vn.edu.ptit.sqa.model.TemplateAM;
import vn.edu.ptit.sqa.model.TemplateResponse;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;

import java.util.List;

public interface EmailTemplateService {
    TemplateResponse createTemplate(TemplateAM templateAM);
    TemplateResponse getEmailTemplateResById(Integer id);

    EmailTemplate getEmailTemplateById(Integer id);
    DataTableResults<TemplateResponse> getAllEmailTemplates(PaginationRequest paginationRequest);
}
