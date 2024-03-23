package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class WaterPriceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date useDay;

    private String description;

    @OneToMany(mappedBy = "waterPriceTable")
    private List<PriceRange> priceRanges;

    @OneToMany(mappedBy = "waterPriceTable")
    private List<Invoice> invoices;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean isDeleted = false;
}
