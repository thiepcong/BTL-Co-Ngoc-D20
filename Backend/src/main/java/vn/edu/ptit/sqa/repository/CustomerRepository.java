package vn.edu.ptit.sqa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.model.waterMeasurementReport.ReportDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByIdInAndIsDeletedFalse(Collection<Long> id);

    Optional<Customer> findByIdAndIsDeletedFasle(Long id);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.waterMeasurementReport.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.newIndex," +
            "       wmi.oldIndex," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN Invoice i ON c.id = i.customer.id" +
            "       LEFT JOIN WaterMeter wm ON ad.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = w.id" +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDelete = FALSE ")
    Page<ReportDTO> findByAddress(String provine, String district, String ward, Pageable pageable);
}
