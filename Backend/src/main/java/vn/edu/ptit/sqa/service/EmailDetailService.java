package vn.edu.ptit.sqa.service;

import vn.edu.ptit.sqa.entity.EmailDetail;
import vn.edu.ptit.sqa.model.AllUserFilter;
import vn.edu.ptit.sqa.model.EmailDetailAM;
import vn.edu.ptit.sqa.model.EmailDetailDto;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;

import java.util.List;

public interface EmailDetailService {
    void createEmailDetail(EmailDetailAM emailDetailAM, AllUserFilter allUserFilter );
    void sendEmail(EmailDetailDto emailDetailDto);

    EmailDetailDto getEmailDetailById(Long id);

    DataTableResults<EmailDetailDto> getAllEmailDetails(PaginationRequest paginationRequest);

}