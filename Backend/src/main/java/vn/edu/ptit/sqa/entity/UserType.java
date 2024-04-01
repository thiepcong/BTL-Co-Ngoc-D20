package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "USER_TYPE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @OneToMany(mappedBy = "userType")
    private List<Customer> customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userType", cascade = CascadeType.ALL)
    private List<PriceList> listPriceLists;
}
