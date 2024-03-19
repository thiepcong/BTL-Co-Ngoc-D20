package vn.edu.ptit.sqa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.ptit.sqa.entity.PriceScale;

public interface PriceScaleRepo extends JpaRepository<PriceScale, Integer>{
	Optional<PriceScale> findById(int id);
}
