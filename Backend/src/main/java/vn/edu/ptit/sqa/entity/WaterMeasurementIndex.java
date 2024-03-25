package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class WaterMeasurementIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long newIndex;

    private Long oldIndex;

    private Date startTime;

    private Date endTime;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    private WaterMeter waterMeter;
}
