package vn.edu.ptit.sqa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.model.reportInfor.DebtCustomerDTO;
import vn.edu.ptit.sqa.model.reportInfor.ReportDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Date;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByIdInAndIsDeletedFalse(Collection<Long> id);

    Optional<Customer> findByIdAndIsDeletedFalse(Long id);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    Page<ReportDTO> findByAddressPage(String provine, String district, String ward, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    List<ReportDTO> findByAddressListAll(String provine, String district, String ward);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND i.status = 'unpaid' " +
            "           AND wmi.startTime >= ?4 " +
            "           AND wmi.endTime <= ?5 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    Page<ReportDTO> findUnPaidCustomerPageByAddressAndTime(String provine, String district, String ward,
                                                           Date start, Date end, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND i.status = 'unpaid' " +
            "           AND wmi.startTime >= ?4 " +
            "           AND wmi.endTime <= ?5 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    List<ReportDTO> findUnPaidCustomerListByAddressAndTime(String provine, String district, String ward,
                                                           Date start, Date end, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND i.status = 'debt' " +
            "           AND wmi.startTime >= ?4 " +
            "           AND wmi.endTime <= ?5 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    List<ReportDTO> findDebtCustomer(String provine, String district, String ward,
                                                           Date start, Date end);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status ) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND i.status = 'debt' " +
            "           AND wmi.startTime >= ?4 " +
            "           AND wmi.endTime <= ?5 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    Page<DebtCustomerDTO> findDebtCustomerPage(String provine, String district, String ward,
                                               Date start, Date end, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND wm.createTime >= ?4 " +
            "           AND wm.createTime <= ?5 " +
            "           AND c.isDeleted = FALSE ")
    Page<ReportDTO> findNewCustomerPage(String provine, String district, String ward,
                                                           Date start, Date end, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeter.id " +
            "       WHERE ad.provine = ?1 " +
            "           AND ad.district = ?2 " +
            "           AND ad.ward = ?3 " +
            "           AND i.status = 'paid' " +
            "           AND wmi.startTime >= ?4 " +
            "           AND wmi.endTime <= ?5 " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    Page<ReportDTO> findPaidCustomerPage(String provine, String district, String ward,
                                     Date start, Date end, Pageable pageable);
}
