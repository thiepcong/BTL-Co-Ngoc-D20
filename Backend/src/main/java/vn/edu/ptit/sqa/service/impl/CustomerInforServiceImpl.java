package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.pagination.PageDto;
import vn.edu.ptit.sqa.model.reportInfor.*;
import vn.edu.ptit.sqa.repository.AddressRepository;
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
            int tmp = i.getEndIndex() - i.getStartIndex();
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

        for(ReportDTO dto : dtoList){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime(), dto.getEndTime());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyPrice(money);
        }

        List<ReportDTO> unpaidList = customerRepository.findUnPaidCustomerListByAddressAndTime(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, request.getSearch());
        for(ReportDTO dto : unpaidList){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime(), dto.getEndTime());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyPrice(money);
        }

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
        for(DebtCustomerDTO dto : allDebtCustomerList){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime(), dto.getEndTime());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setDebtMoneyNumber(money);
        }

        for(DebtCustomerDTO dto : allDebtCustomers){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime(), dto.getEndTime());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
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
        Page<ReportDTO> paidCustomerPage = customerRepository.findPaidCustomerPage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, request.getSearch(), pageable);
        List<ReportDTO> paidCusList = paidCustomerPage.getContent();
        for(ReportDTO dto : paidCusList){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime(), dto.getEndTime());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyPrice(money);
        }
        List<ReportDTO> allPaidCustomer = customerRepository.findAllPaidCustomer(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end);

        for (ReportDTO dto : allPaidCustomer) {
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if (customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStartTime(), dto.getEndTime());
            if (optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyPrice(money);
        }

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
        for(RevenueDTO dto : allRevenues){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStart(), dto.getEnd());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
            float money = getMoney(dto.getNewWaterUsageIndex(), dto.getOldWaterUsageIndex(), priceScales);
            dto.setMoneyNumber(money);
        }
        Page<RevenueDTO> revenueDTOPage = customerRepository.findRevenuePage(request.getProvine(),
                request.getDistrict(), request.getWard(), start, end, pageable);
        List<RevenueDTO> dtos = revenueDTOPage.getContent();
        for(RevenueDTO dto : dtos){
            Long customerId = dto.getCustomerId();
            Optional<Customer> customerOptional = customerRepository.findByIdAndIsDeletedFalse(customerId);
            if(customerOptional.isEmpty()) throw new RuntimeException("Not found customer to solve money");
            Customer customer = customerOptional.get();
            Optional<PriceList> optionalPriceList = priceListRepo.findByUserType(customer.getUserType().getId(),
                    dto.getStart(), dto.getEnd());
            if(optionalPriceList.isEmpty()) throw new RuntimeException("PriceList is not found, can't solve money");
            List<PriceScale> priceScales = optionalPriceList.get().getListPriceScales();
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

}
