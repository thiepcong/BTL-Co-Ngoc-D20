package vn.edu.ptit.sqa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.sqa.entity.PriceList;

@Repository
public interface PriceListRepo extends JpaRepository<PriceList, Integer>{
	Optional<PriceList> findById(int id);

}
