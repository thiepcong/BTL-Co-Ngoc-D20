package vn.edu.ptit.sqa.service.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import vn.edu.ptit.sqa.entity.EmailDetail;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.EmailDetailDto;
import vn.edu.ptit.sqa.model.pagination.DataTableResults;
import vn.edu.ptit.sqa.model.pagination.PaginationRequest;
import vn.edu.ptit.sqa.repository.EmailDetailRepo;
import vn.edu.ptit.sqa.service.EmailDetailService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmailDetailServiceImplTest {

    @InjectMocks
    private EmailDetailServiceImpl emailDetailService;

    @Mock
    private EmailDetailRepo emailDetailRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getEmailDetailById_HappyPath() {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setId(1L);
        when(emailDetailRepo.findById(any(Long.class))).thenReturn(Optional.of(emailDetail));

        EmailDetailDto result = emailDetailService.getEmailDetailById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void getEmailDetailById_NotFound() {
        when(emailDetailRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> emailDetailService.getEmailDetailById(1L));
    }

    @Test
    public void getAllEmailDetails_HappyPath() {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setId(1L);
        Page<EmailDetail> page = new PageImpl<>(Collections.singletonList(emailDetail));
        when(emailDetailRepo.findAll(any(PageRequest.class))).thenReturn(page);

        PaginationRequest paginationRequest = new PaginationRequest();
        paginationRequest.setPageNum(1);
        paginationRequest.setPageSize(1);

        DataTableResults<EmailDetailDto> result = emailDetailService.getAllEmailDetails(paginationRequest);

        assertEquals(1, result.getTotalItems());
        assertEquals(1, result.getCurrentPage());
    }
}