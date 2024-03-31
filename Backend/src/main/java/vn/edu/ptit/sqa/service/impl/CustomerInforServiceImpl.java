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
        Page<ReportDTO> reportDtoPage = customerRepository.findByAddressPage(request.getProvine(), request.getDistrict(),
                request.getWard(), request.getSearch(),  pageable);
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
                request.getDistrict(), request.getWard(), start, end, request.getSearch(), pageable);
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
    public DebtReportResponse getDebtCustomerList(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Page<DebtCustomerDTO> allDebtCustomerList = customerRepository.findDebtCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        List<DebtCustomerDTO> debtCustomerDTOList = allDebtCustomerList.getContent();
        List<DebtCustomerDTO> allDebtCustomers = customerRepository.findAllDebtCustomer(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
        long totalDebtMoney = allDebtCustomers.stream().mapToLong(DebtCustomerDTO::getDebtMoneyNumber).sum();
        DebtReportResponse response = new DebtReportResponse();
        response.setDebtCustomerList(debtCustomerDTOList);
        response.setTotalDebtNumber(totalDebtMoney);
        response.setPageDto(PageDto.populatePageDto(allDebtCustomerList));
        return response;
    }

    @Override
    public NewCustomerResponse getNewCustomerList(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        NewCustomerResponse response = new NewCustomerResponse();
        Page<NewCutomerDTO> newCustomerPage = customerRepository.findNewCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        response.setNewCutomerDTOList(newCustomerPage.getContent());
        response.setPageDto(PageDto.populatePageDto(newCustomerPage));
        return response;
    }

    @Override
    public NewCustomerReportDTO getNewCustomerNumber(ReportInforRequest request) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        List<ReportDTO> allCustomerList = customerRepository.findByAddressListAll(request.getProvine(),
                request.getDistrict(), request.getWard());
        int allNum = allCustomerList.size();
        System.out.println(allNum);
        List<ReportDTO> allNewCustomer = customerRepository.findNewCustomer(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
        int newCustomerNum = allNewCustomer.size();
        NewCustomerReportDTO response = new NewCustomerReportDTO();
        response.setNewCustomerNum(newCustomerNum);
        response.setAllCustomerNum(allNum);
        double percent = (double) newCustomerNum / allNum * 100;
        response.setPercent(percent + "%");
        return response;
    }

    @Override
    public ReportInforResponse getPaidCustomer(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        ReportInforResponse response = new ReportInforResponse();
        Page<ReportDTO> newCustomerPage = customerRepository.findPaidCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, request.getSearch(), pageable);
        response.setReportDTOList(newCustomerPage.getContent());
        response.setPageDto(PageDto.populatePageDto(newCustomerPage));
        return response;
    }

    @Override
    public RevenueResponse getRevenue(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        List<RevenueDTO> allRevenues = customerRepository.findAllRevenue(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
        Page<RevenueDTO> revenueDTOPage = customerRepository.findRevenuePage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        List<RevenueDTO> dtos = revenueDTOPage.getContent();
        Long totalMoney = allRevenues.stream().mapToLong(RevenueDTO::getMoneyNumber).sum();
        RevenueResponse response = new RevenueResponse();
        response.setRevenueDTOList(dtos);
        response.setTotalMoney(totalMoney);
        response.setPageDto(PageDto.populatePageDto(revenueDTOPage));
        return response;
    }

}
