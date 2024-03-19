package vn.edu.ptit.sqa.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PRICE_LIST")
public class PriceList  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "APPLY_DATE")
    private Date applyDate;
    
    private String type;
    
    private String description;
    
    @JoinColumn(name = "USER_ID")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private User user;
}
