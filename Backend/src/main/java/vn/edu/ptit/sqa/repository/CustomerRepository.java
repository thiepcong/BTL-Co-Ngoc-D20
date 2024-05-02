package vn.edu.ptit.sqa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.ptit.sqa.entity.Customer;
import vn.edu.ptit.sqa.model.reportInfor.DebtCustomerDTO;
import vn.edu.ptit.sqa.model.reportInfor.NewCutomerDTO;
import vn.edu.ptit.sqa.model.reportInfor.ReportDTO;
import vn.edu.ptit.sqa.model.reportInfor.RevenueDTO;

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
            "       wm.createTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND ((:search IS NULL or c.name like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.phone like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.email like concat('%', :search, '%'))) " +
            "           AND (:end IS NULL OR wm.createTime <= :end) " +
            "           AND (:start IS NULL OR wmi.startTime >= :start ) " +
            "           AND (:end IS NULL OR wmi.endTime <= :end) " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE ")
    Page<ReportDTO> findByAddressPage(String province, String district, String ward, String search,
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
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND (:end IS NULL OR wm.createTime <= :end) " +
            "           AND (:start IS NULL OR wmi.startTime >= :start ) " +
            "           AND (:end IS NULL OR wmi.endTime <= :end) " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name ")
    List<ReportDTO> findByAddressListAll(String province, String district, String ward, Date start, Date end);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex, " +
            "       wmi.startTime, " +
            "       wmi.endTime, " +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.id = wm.address.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE (:province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'unpaid' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND ((:search IS NULL or c.name like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.phone like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.email like concat('%', :search, '%'))) " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "           ORDER BY c.name ")
    Page<ReportDTO> findUnPaidCustomerPageByAddressAndTime(String province, String district, String ward,
                                                           Date start, Date end, String search, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex, " +
            "       wmi.startTime, " +
            "       wmi.endTime, " +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'unpaid' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND ((:search IS NULL or c.name like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.phone like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.email like concat('%', :search, '%'))) " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "        ORDER BY c.name")
    List<ReportDTO> findUnPaidCustomerListByAddressAndTime(String province, String district, String ward,
                                                           Date start, Date end, String search);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex, " +
            "       wmi.startTime, " +
            "       wmi.endTime, " +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'debt' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name")
    List<ReportDTO> findDebtCustomer(String province, String district, String ward,
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
            "       wm.createTime," +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND wm.createTime >= :start " +
            "           AND wm.createTime <= :end " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name")
    List<ReportDTO> findNewCustomer(String province, String district, String ward,
                                     Date start, Date end);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.DebtCustomerDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward, " +
            "       wmi.newIndex," +
            "       wmi.oldIndex, " +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status ) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'debt' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name")
    Page<DebtCustomerDTO> findDebtCustomerPage(String province, String district, String ward,
                                               Date start, Date end, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.DebtCustomerDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward, " +
            "       wmi.newIndex," +
            "       wmi.oldIndex," +
            "       wmi.startTime," +
            "       wmi.endTime," +
            "       i.status ) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'debt' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name")
    List<DebtCustomerDTO> findAllDebtCustomer(String province, String district, String ward,
                                               Date start, Date end);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.NewCutomerDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine," +
            "       ad.district," +
            "       ad.ward," +
            "       wm.waterUsageNumber," +
            "       wm.createTime) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND wm.createTime >= :start " +
            "           AND wm.createTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "       ORDER BY c.name")
    Page<NewCutomerDTO> findNewCustomerPage(String province, String district, String ward,
                                            Date start, Date end, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex, " +
            "       wmi.startTime, " +
            "       wmi.endTime, " +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE (:province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'paid' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND ((:search IS NULL or c.name like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.phone like concat('%', :search, '%')) " +
            "               OR (:search IS NULL or c.email like concat('%', :search, '%'))) " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "           ORDER BY c.name ")
    Page<ReportDTO> findPaidCustomerPage(String province, String district, String ward,
                                     Date start, Date end, String search, Pageable pageable);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.ReportDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email," +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex, " +
            "       wmi.startTime, " +
            "       wmi.endTime, " +
            "       i.status) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE (:province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND i.status = 'paid' " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "           ORDER BY c.name ")
    List<ReportDTO> findAllPaidCustomer(String province, String district, String ward,
                                         Date start, Date end);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.RevenueDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email, " +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex," +
            "       wmi.startTime," +
            "       wmi.endTime, " +
            "       i.status ) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name")
    List<RevenueDTO> findAllRevenue(String province, String district, String ward,
                                    Date start, Date end);

    @Query(value = "SELECT new vn.edu.ptit.sqa.model.reportInfor.RevenueDTO(" +
            "       c.id, " +
            "       c.name, " +
            "       c.phone, " +
            "       c.email, " +
            "       ad.provine, " +
            "       ad.district, " +
            "       ad.ward, " +
            "       wmi.newIndex, " +
            "       wmi.oldIndex," +
            "       wmi.startTime," +
            "       wmi.endTime, " +
            "       i.status ) " +
            "       FROM Customer c " +
            "       LEFT JOIN Address ad ON c.id = ad.customer.id " +
            "       LEFT JOIN WaterMeter wm ON ad.waterMeter.id = wm.id" +
            "       LEFT JOIN WaterMeasurementIndex wmi ON wmi.waterMeter.id = wm.id " +
            "       LEFT JOIN Invoice i ON wm.id = i.waterMeasurementIndex.id " +
            "       WHERE ( :province IS NULL OR  ad.provine = :province)  " +
            "           AND (:district IS NULL OR ad.district = :district) " +
            "           AND ( :ward IS NULL OR ad.ward = :ward)  " +
            "           AND wmi.startTime >= :start " +
            "           AND wmi.endTime <= :end " +
            "           AND c.isDeleted = FALSE " +
            "           AND i.isDeleted = FALSE " +
            "       ORDER BY c.name")
    Page<RevenueDTO> findRevenuePage(String province, String district, String ward,
                                    Date start, Date end, Pageable pageable);
}
