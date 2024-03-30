package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
public class NewCustomerResponse {
    private NewCutomerDTO newCutomerDTO;
    private List<NewCutomerDTO> newCutomerDTOList;
    private Long totalNewNumber;
    private PageDto pageDto;
}
