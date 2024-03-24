package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.pagination.PageDto;
import vn.edu.ptit.sqa.model.reportInfor.*;
import vn.edu.ptit.sqa.repository.AddressRepository;
import vn.edu.ptit.sqa.repository.CustomerRepository;
import vn.edu.ptit.sqa.service.CustomerInforService;
import vn.edu.ptit.sqa.util.DateUtils;

import java.util.Date;
import java.util.List;

@Service
public class CustomerInforServiceImpl implements CustomerInforService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public ReportInforResponse getReportByAddress(ReportInforRequest request, Pageable pageable) {
        Page<ReportDTO> reportDtoPage = customerRepository.findByAddressPage(request.getProvine(), request.getDistrict(), request.getWard(), pageable);
        List<ReportDTO> dtoList = reportDtoPage.getContent();
        if(dtoList == null) {
            throw new NotFoundException("Err: List is null");
        }
        PageDto pageDto = new PageDto();
        ReportInforResponse response = new ReportInforResponse();
        response.setReportDTOList(dtoList);
        response.setPageDto(pageDto.populatePageDto(reportDtoPage));
        return response;
    }

    @Override
    public ReportInforResponse getUnPaidClientList(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Page<ReportDTO> reportDTOPage = customerRepository.findUnPaidCustomerPageByAddressAndTime(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end , pageable);
        List<ReportDTO> dtoList = reportDTOPage.getContent();
        if(dtoList == null) {
            throw new NotFoundException("Err: List is null");
        }
        ReportInforResponse response = new ReportInforResponse();
        response.setReportDTOList(dtoList);
        response.setPageDto(PageDto.populatePageDto(reportDTOPage));
        return response;
    }

    @Override
    public DebtReportDTO getDebtCustomerNumber(ReportInforRequest request) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        List<ReportDTO> allCustomerList = customerRepository.findByAddressListAll(request.getProvine(),
                request.getDistrict(), request.getWard());
        int allNum = allCustomerList.size();
        System.out.println(allNum);
        List<ReportDTO> allDebtCustomer = customerRepository.findDebtCustomer(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
        int debtNum = allDebtCustomer.size();
        DebtReportDTO response = new DebtReportDTO();
        response.setDebtNum(debtNum);
        response.setAllCustomerNum(allNum);
        double percent = (double) debtNum / allNum * 100;
        response.setPercent(percent + "%");
        return response;
    }

    @Override
    public DebtReportRespone getDebtCustomerList(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Page<DebtCustomerDTO> allDebtCustomerList = customerRepository.findDebtCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        List<DebtCustomerDTO> debtCustomerDTOList = allDebtCustomerList.getContent();
        DebtReportRespone response = new DebtReportRespone();
        response.setDebtCustomerList(allDebtCustomerList.getContent());
        response.setPageDto(PageDto.populatePageDto(allDebtCustomerList));
        return response;
    }

    @Override
    public ReportInforResponse getNewCustomer(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        ReportInforResponse response = new ReportInforResponse();
        Page<ReportDTO> newCustomerPage = customerRepository.findNewCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        response.setReportDTOList(newCustomerPage.getContent());
        response.setPageDto(PageDto.populatePageDto(newCustomerPage));
        return response;
    }

    @Override
    public ReportInforResponse getPaidCustomer(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        ReportInforResponse response = new ReportInforResponse();
        Page<ReportDTO> newCustomerPage = customerRepository.findPaidCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        response.setReportDTOList(newCustomerPage.getContent());
        response.setPageDto(PageDto.populatePageDto(newCustomerPage));
        return response;
    }

}
