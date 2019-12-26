package com.syphan.luu.something;

import com.syphan.practice.common.dao.JpaQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface OmsOrderRepository2 extends JpaQueryRepository<OmsOrder, Long> {

    /**
     * native query
    @Query("select sum(o.total_amount) from oms_order o where (:orderNum is null or o.order_num like %:orderNum%)" +
            "and (:userId is null or o.user_id = :userId) and (:bookingName is null or o.booking_name like %:bookingName%)" +
            "and (:bookingPhoneNum is null or o.booking_phone_num like %:bookingPhoneNum%) and (:storeId is null or o.store_id = :storeId)" +
            "and (:storeName is null or o.store_name like %:storeName%) and (:stylistId is null or o.stylist_id = :stylistId)" +
            "and (:stylistName is null or o.stylist_name like %:stylistName%) and (:statusIsEmpty is null or o.status in :status)" +
            "and (:evaluated is null or o.is_evaluated = :evaluated) " +
            "and (:staffId is null or o.id in (SELECT o.order_id from oms_order_item o WHERE o.nursing_staff_id = :staffId GROUP by o.order_id))" +
            "and (:startDate is null or o.booking_time >= :startDate) and (:endDate is null or o.booking_time <= :endDate)", nativeQuery = true)
    fun getSumTotalAmount(@Param("orderNum")orderNum: String?, @Param("userId")userId: Long?,
                          @Param("bookingName")bookingName: String?, @Param("bookingPhoneNum")bookingPhoneNum: String?,
                          @Param("storeId")storeId: Long?, @Param("storeName")storeName: String?,
                          @Param("stylistId")stylistId: Long?, @Param("stylistName")stylistName: String?,
                          @Param("statusIsEmpty")statusIsEmpty: String?, @Param("status")status: List<Int>,
                          @Param("evaluated")evaluated: Boolean?, @Param("staffId")staffId: Long?,
                          @Param("startDate")startDate: Timestamp?, @Param("endDate")endDate: Timestamp?): BigDecimal?

    @Query("select * from oms_order o where (:orderNum is null or o.order_num like %:orderNum%)" +
            "and (:userId is null or o.user_id = :userId) and (:bookingName is null or o.booking_name like %:bookingName%)" +
            "and (:bookingPhoneNum is null or o.booking_phone_num like %:bookingPhoneNum%) and (:storeId is null or o.store_id = :storeId)" +
            "and (:storeName is null or o.store_name like %:storeName%) and (:stylistId is null or o.stylist_id = :stylistId)" +
            "and (:stylistName is null or o.stylist_name like %:stylistName%) and (:statusIsEmpty is null or o.status in :status)" +
            "and (:evaluated is null or o.is_evaluated = :evaluated) " +
            "and (:staffId is null or o.id in (SELECT o.order_id from oms_order_item o WHERE o.nursing_staff_id = :staffId GROUP by o.order_id))" +
            "and (:startDate is null or o.booking_time >= :startDate) and (:endDate is null or o.booking_time <= :endDate)", nativeQuery = true)
    fun findAllOrder(@Param("orderNum") orderNum: String?, @Param("userId") userId: Long?,
                     @Param("bookingName") bookingName: String?, @Param("bookingPhoneNum") bookingPhoneNum: String?,
                     @Param("storeId") storeId: Long?, @Param("storeName") storeName: String?,
                     @Param("stylistId") stylistId: Long?, @Param("stylistName") stylistName: String?,
                     @Param("statusIsEmpty") statusIsEmpty: String?, @Param("status") status: List<Int>,
                     @Param("evaluated") evaluated: Boolean?, @Param("staffId") staffId: Long?,
                     @Param("startDate") startDate: Timestamp?, @Param("endDate") endDate: Timestamp?, pageable: Pageable): Page<OmsOrder>
     */

    /**
     * JPA query
     * @Query("select w from Warehouse w where (:name is null or w.name like %:name%) " +
     *             "and (:address is null or w.address like %:address%) and (:status is null or w.status = :status) and (:storeId is null or w.id in :warehouseIds)")
     *     fun findAllByNameAndAddressAndStatus(
     *             @Param("name") name: String?,
     *             @Param("address") address: String?,
     *             @Param("status") status: Int?,
     *             @Param("storeId") storeId: Long?,
     *             @Param("warehouseIds") warehouseIds: List<Long>,
     *             pageable: Pageable
     *     ): Page<Warehouse>
     */


    /**
    @Query(value = "select iv.* from public.invoice iv inner join public.orders od on iv.orders_id = od.id and od.store_id = ?1 " +
            "and (?2 is null or od.order_ref = cast(?2 as varchar))", nativeQuery = true)
    Page<Invoice> getInvoiceBySomething(@NotNull Long storeId, String invoiceRef, Pageable pageable);
    */
}
