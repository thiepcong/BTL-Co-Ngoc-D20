package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
public class RevenueResponse {
    private RevenueDTO revenueDTO;
    private List<RevenueDTO> revenueDTOList;
    private float totalMoney;
    private PageDto pageDto;
}
