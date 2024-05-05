package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.exception.ExistedException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.pagination.PageDto;
import vn.edu.ptit.sqa.model.reportInfor.*;
import vn.edu.ptit.sqa.repository.CustomerRepository;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.service.CustomerInforService;
import vn.edu.ptit.sqa.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerInforServiceImpl implements CustomerInforService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PriceListRepo priceListRepo;
    public static float getMoney(Long newIndex, Long oldIndex, List<PriceScale> priceScales){
        float money = 0L;
        Long usageIndex = newIndex - oldIndex;
        for(PriceScale i : priceScales){
            if(usageIndex <= 0) break;
            int tmp;

            if(i.getStartIndex() == 0) {
                tmp = i.getEndIndex() - i.getStartIndex();
            }
            else{
                tmp = i.getEndIndex() - i.getStartIndex() + 1;
            }

            if(usageIndex <= tmp) {
                money += usageIndex * i.getPrice();
                break;
            }
            money += tmp * i.getPrice();
            usageIndex = usageIndex - tmp;
        }
        return money;
    }
    @Override
    public ReportInforResponse getReportByAddress(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        Page<ReportDTO> reportDtoPage = customerRepository.findByAddressPage(request.getProvine(), request.getDistrict(),
                request.getWard(), request.getSearch(), start, end,  pageable);
        if(reportDtoPage == null) {
            throw new NotFoundException("Err: List is null");
        }
        List<ReportDTO> dtoList = reportDtoPage.getContent();

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

        if(reportDTOPage == null) {
            throw new NotFoundException("Err: List is null");
        }
        List<ReportDTO> dtoList = reportDTOPage.getContent();
        dtoList = setMoneyForList(dtoList);
        List<ReportDTO> unpaidList = customerRepository.findUnPaidCustomerListByAddressAndTime(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, request.getSearch());

        unpaidList = setMoneyForList(unpaidList);
        float totalMoney = (float) unpaidList.stream().mapToDouble(ReportDTO::getMoneyPrice).sum();
        ReportInforResponse response = new ReportInforResponse();
        response.setReportDTOList(dtoList);
        response.setPageDto(PageDto.populatePageDto(reportDTOPage));
        response.setTotalMoney(totalMoney);
        return response;
    }

    @Override
    public DebtReportDTO getDebtCustomerNumber(ReportInforRequest request) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        List<ReportDTO> allCustomerList = customerRepository.findByAddressListAll(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
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
        if(allDebtCustomerList == null) {
            throw new NotFoundException("No debt customer!!");
        }
        List<DebtCustomerDTO> debtCustomerDTOList = allDebtCustomerList.getContent();
        List<DebtCustomerDTO> allDebtCustomers = customerRepository.findAllDebtCustomer(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
        for(DebtCustomerDTO dto : allDebtCustomerList){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new NotFoundException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            List<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime());
            if(optionalPriceList.isEmpty()) throw new ExistedException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get(0).getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setDebtMoneyNumber(money);
        }

        for(DebtCustomerDTO dto : allDebtCustomers){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new NotFoundException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            List<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime());
            if(optionalPriceList.isEmpty()) throw new ExistedException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get(0).getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setDebtMoneyNumber(money);
        }
        float totalDebtMoney = (float) allDebtCustomers.stream().mapToDouble(DebtCustomerDTO::getDebtMoneyNumber).sum();
        DebtReportResponse response = new DebtReportResponse();
        response.setDebtCustomerList(debtCustomerDTOList);
        response.setTotalDebtNumber(totalDebtMoney);
        response.setPageDto(PageDto.populatePageDto(allDebtCustomerList));
        return response;
    }

    @Override
    public newCustomerResponse getNewCustomerList(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        newCustomerResponse response = new newCustomerResponse();
        Page<NewCustomerDTO> newCustomerPage = customerRepository.findNewCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        response.setNewCustomerDTOList(newCustomerPage.getContent());
        response.setPageDto(PageDto.populatePageDto(newCustomerPage));
        return response;
    }

    @Override
    public NewCustomerReportDTO getNewCustomerNumber(ReportInforRequest request) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        List<ReportDTO> allCustomerList = customerRepository.findByAddressListAll(request.getProvine(),
                request.getDistrict(), request.getWard(), null, null);
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
        Page<ReportDTO> paidCustomerPage = customerRepository.findPaidCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, request.getSearch(), pageable);
        if(paidCustomerPage == null){
            throw new NotFoundException("Err: List is null");
        }
        List<ReportDTO> paidCusList = paidCustomerPage.getContent();
        paidCusList = setMoneyForList(paidCusList);
        List<ReportDTO> allPaidCustomer = customerRepository.findAllPaidCustomer(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);

        allPaidCustomer = setMoneyForList(allPaidCustomer);
        float totalMoney = (float) allPaidCustomer.stream().mapToDouble(ReportDTO::getMoneyPrice).sum();
        response.setReportDTOList(paidCusList);
        response.setPageDto(PageDto.populatePageDto(paidCustomerPage));
        response.setTotalMoney(totalMoney);
        return response;
    }

    @Override
    public RevenueResponse getRevenue(ReportInforRequest request, Pageable pageable) {
        Date start = DateUtils.getStartDayOfMonthFromCurrentDate(request.getMonth());
        Date end = DateUtils.getEndDayOfMonthFromCurrentDate(request.getMonth());
        List<RevenueDTO> allRevenues = customerRepository.findAllRevenue(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);
        if(allRevenues == null){
            throw new NotFoundException("Revenue empty!!!");
        }
        for(RevenueDTO dto : allRevenues){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new NotFoundException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            List<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStart());
            System.out.println(optionalPriceList);
            if(optionalPriceList.isEmpty()) throw new ExistedException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get(0).getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyNumber(money);
        }


        Page<RevenueDTO> revenueDTOPage = customerRepository.findRevenuePage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        List<RevenueDTO> dtos = revenueDTOPage.getContent();
        for(RevenueDTO dto : dtos){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new NotFoundException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            List<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStart());
            if(optionalPriceList.isEmpty()) throw new ExistedException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get(0).getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyNumber(money);
        }
        float totalMoney = (float) allRevenues.stream().mapToDouble(RevenueDTO::getMoneyNumber).sum();
        RevenueResponse response = new RevenueResponse();
        response.setRevenueDTOList(dtos);
        response.setTotalMoney(totalMoney);
        response.setPageDto(PageDto.populatePageDto(revenueDTOPage));
        return response;
    }

    public List<ReportDTO> setMoneyForList(List<ReportDTO> reportDTOS){
        for(ReportDTO dto : reportDTOS){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new NotFoundException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            List<PriceList> optionalPriceList = priceListRepo.findByUserType(
                    customer.getUserType().getId(),
                    dto.getStartTime());
            if(optionalPriceList.isEmpty()) throw new ExistedException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get(0).getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyPrice(money);
        }
        return reportDTOS;
    }

}
