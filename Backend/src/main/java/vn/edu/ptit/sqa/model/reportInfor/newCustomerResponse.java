package vn.edu.ptit.sqa.model.reportInfor;

import lombok.Data;
import vn.edu.ptit.sqa.model.pagination.PageDto;

import java.util.List;

@Data
public class newCustomerResponse {
    private NewCustomerDTO newCustomerDTO;
    private List<NewCustomerDTO> newCustomerDTOList;
    private Long totalNewNumber;
    private PageDto pageDto;
}
