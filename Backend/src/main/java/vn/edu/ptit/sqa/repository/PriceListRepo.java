package vn.edu.ptit.sqa.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.sqa.entity.PriceList;

@Repository
public interface PriceListRepo extends JpaRepository<PriceList, Integer>{
	Optional<PriceList> findById(int id);

	@Query(value = "SELECT p " +
			"		FROM PriceList p " +
			"		WHERE p.userType.id = :userTypeId " +
			"			AND p.status = 1 " +
			"			AND p.applyDate <= :start " +
			"		ORDER BY p.applyDate DESC ")
	List<PriceList> findByUserType(Integer userTypeId, Date start);
}
