package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class WaterMeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createTime;

    private Long waterUsageNumber;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "waterMeter")
    private List<WaterMeasurementIndex> waterMeasurementIndexList;
}
