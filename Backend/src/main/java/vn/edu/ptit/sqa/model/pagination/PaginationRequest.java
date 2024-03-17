package vn.edu.ptit.sqa.model.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.ptit.sqa.config.AppProperties;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PaginationRequest implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalRecords;
    @JsonProperty
    private boolean isPaginated;
    private boolean paginated;

    public Integer getPageNum() {
        return (null == pageNum || pageNum <= 0) ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return (null == pageSize) ? AppProperties.PAGINATION.PAGE_SIZE : pageSize;
    }

    public void setPaginated(boolean isPaginated) {
        this.isPaginated = isPaginated;
    }

    public PaginationRequest(boolean isPaginated) {
        this.isPaginated = isPaginated;
    }

    public PaginationRequest(boolean isPaginated, Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.isPaginated = isPaginated;
    }

}