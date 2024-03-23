package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Long waterIndex;

    private Date invoiceTime;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private User staff;

    @ManyToOne(fetch = FetchType.LAZY)
    private WaterPriceTable waterPriceTable;

    private boolean isDelete = false;
}
