package vn.edu.ptit.sqa.entity;
import jakarta.persistence.*;
import lombok.Getter;
import vn.edu.ptit.sqa.enums.RoleName;

@Getter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private RoleName name;


}