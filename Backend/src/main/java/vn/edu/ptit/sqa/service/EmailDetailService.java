package vn.edu.ptit.sqa.service;

import vn.edu.ptit.sqa.entity.EmailDetail;
import vn.edu.ptit.sqa.model.AllUserFilter;
import vn.edu.ptit.sqa.model.EmailDetailAM;
import vn.edu.ptit.sqa.model.EmailDetailDto;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;

import java.util.List;

public interface EmailDetailService {
    Boolean createEmailDetail(EmailDetailAM emailDetailAM);
    Boolean sendEmail(EmailDetailDto emailDetailDto);

    EmailDetailDto getEmailDetailById(Long id);

    DataTableResults<EmailDetailDto> getAllEmailDetails(PaginationRequest paginationRequest);

}
