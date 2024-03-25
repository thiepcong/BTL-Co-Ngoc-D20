package vn.edu.ptit.sqa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PriceRange {
    @Id
    private Long id;

    private Long fistIndex;

    private Long lastIndex;

    private Long price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private WaterPriceTable waterPriceTable;

    private boolean isDeleted = false;
}
