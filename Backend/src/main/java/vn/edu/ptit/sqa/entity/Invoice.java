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

    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne
    private WaterMeter waterMeter;

    @ManyToOne(fetch = FetchType.LAZY)
    private User staff;

    @ManyToOne(fetch = FetchType.LAZY)
    private PriceList priceList;

    private boolean isDeleted = false;
}
