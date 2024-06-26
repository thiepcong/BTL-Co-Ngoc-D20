package vn.edu.ptit.sqa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "phone_number")
    private String phone;

    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserType userType;

    private boolean isDeleted;
}
