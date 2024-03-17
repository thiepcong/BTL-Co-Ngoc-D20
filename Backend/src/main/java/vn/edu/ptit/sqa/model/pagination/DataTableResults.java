package vn.edu.ptit.sqa.model.pagination;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataTableResults<T> {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalItems;
    List<T> data;


    public DataTableResults(List<T> data) {
        this.data = data;
    }

    public DataTableResults() {
    }
}
