package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PRICE_SCALE")
public class PriceScale  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "START_INDEX")
    private Integer startIndex;
    
    @Column(name = "END_INDEX")
    private Integer endIndex;
    
    private float price;
    
    private String description;
    
    @JoinColumn(name = "PRICE_LIST_ID")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private PriceList priceList;
}
