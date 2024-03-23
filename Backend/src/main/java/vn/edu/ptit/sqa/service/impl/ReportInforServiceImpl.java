package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.pagination.PageDto;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportDTO;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportInforRequest;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportInforResponse;
import vn.edu.ptit.sqa.repository.AddressRepository;
import vn.edu.ptit.sqa.repository.CustomerRepository;
import vn.edu.ptit.sqa.service.ReportInforService;

import java.util.List;

@Service
public class ReportInforServiceImpl implements ReportInforService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public ReportInforResponse getReportByAddress(ReportInforRequest request, Pageable pageable) {
        Page<ReportDTO> reportDtoPage = customerRepository.findByAddress(request.getProvine(), request.getDistrict(), request.getWard(), pageable);
        List<ReportDTO> dtoList = reportDtoPage.getContent();
        if(dtoList == null) {
            throw new NotFoundException("Err: List is null");
        }
        PageDto pageDto = new PageDto();
        pageDto.populatePageDto(reportDtoPage);
        ReportInforResponse response = new ReportInforResponse();
        response.setReportDTOList(dtoList);
        response.setPageDto(pageDto);
        return response;
    }


}
