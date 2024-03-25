package vn.edu.ptit.sqa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.ptit.sqa.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Override
    Optional<Address> findById(Long id);

//    @Query(value = "SELECT a " +
//            "       FROM Address a " +
//            "       WHERE a.provine = ?1 " +
//            "       AND a.district = ?2 " +
//            "       AND a.ward = ?3 " +
//            "       AND a.isDeleted = false ")
//    List<Address> findByProvineAndDistrictAndWard(String provine, String district, String ward);


}
