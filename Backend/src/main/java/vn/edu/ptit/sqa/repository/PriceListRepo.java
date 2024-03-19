package vn.edu.ptit.sqa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.ptit.sqa.entity.PriceList;

public interface PriceListRepo extends JpaRepository<PriceList, Integer>{
	Optional<PriceList> findById(int id);
}
