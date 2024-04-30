package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.entity.PriceList;
import vn.edu.ptit.sqa.entity.PriceScale;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.model.PriceScaleRequest;
import vn.edu.ptit.sqa.model.PriceScaleResponse;
import vn.edu.ptit.sqa.repository.PriceListRepo;
import vn.edu.ptit.sqa.repository.PriceScaleRepo;
import vn.edu.ptit.sqa.service.PriceScaleService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.util.List;

@Service
public class PriceScaleServiceImpl implements PriceScaleService {
    @Autowired
    PriceListRepo priceListRepo;
    @Autowired
    PriceScaleRepo priceScaleRepo;
    @Override
    public List<PriceScaleResponse> createPriceScale(Integer priceListId, List<PriceScaleRequest> priceScaleRequests) {
        PriceList priceList = priceListRepo.findById(priceListId)
                .orElseThrow(() -> new NotFoundException("price list id " + priceListId));
        int currThreshold = 0;
        for(PriceScaleRequest priceScaleRequest : priceScaleRequests){
            if(priceScaleRequest.getStartIndex() > currThreshold + 1){
                throw new BadRequestException("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị");
            }else if(priceScaleRequest.getStartIndex() < currThreshold + 1 && currThreshold != 0){
                throw new BadRequestException("Chỉ số đầu phải lớn hơn chỉ số cuối bậc trước 1 đơn vị");
            }else if(priceScaleRequest.getStartIndex() >= priceScaleRequest.getEndIndex()) {
                    throw new BadRequestException("Chỉ số đầu phải bé hơn chỉ số cuối");
            }
            currThreshold = priceScaleRequest.getEndIndex();
        }
        priceScaleRequests.get(priceScaleRequests.size()-1).setEndIndex(10000000);
        List<PriceScale> priceScales = ConverterUtil.mapList(priceScaleRequests, PriceScale.class);
        priceScales.forEach(priceScale -> {
            priceScale.setPriceList(priceList);
        });
        priceScaleRepo.saveAll(priceScales);
        return ConverterUtil.mapList(priceScales, PriceScaleResponse.class);
    }

    @Override
    public PriceScaleResponse getPriceScaleById(Integer id) {
        PriceScale priceScale = priceScaleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("price scale id " + id));
        return ConverterUtil.mappingToObject(priceScale, PriceScaleResponse.class);
    }
}
