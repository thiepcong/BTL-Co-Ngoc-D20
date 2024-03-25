package vn.edu.ptit.sqa.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRICE_LIST")
public class PriceList  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "APPLY_DATE")
    private Date applyDate;

    @Column(name = "STATUS")
    private Integer status;
    
    @JoinColumn(name = "USER_ID")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priceList", cascade = CascadeType.ALL)
    private List<PriceScale> listPriceScales;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_TYPE_ID")
    private UserType userType;
}
