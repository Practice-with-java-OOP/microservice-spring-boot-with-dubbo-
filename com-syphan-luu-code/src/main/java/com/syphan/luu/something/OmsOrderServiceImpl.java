package com.syphan.luu.something;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class OmsOrderServiceImpl {
    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    /**
     * create query using Transformer
    @Suppress("UNCHECKED_CAST")
    override fun getQuantityMaxUsed(bookingTime:Date, discountIds:List<Long>, status:List<Int>):List<OrderDiscountQuantityMaxUsed>{
        val query = "SELECT " +
                "monthly.coupon_id as discountId, " +
                "COALESCE(daily.countDay, 0) as countMaxDaily, " +
                "COALESCE(weekly.countWeek, 0) as countMaxWeekly, " +
                "monthly.countMonthly as countMaxMonthly " +
                "from (SELECT coupon_id, COUNT(coupon_id) as countMonthly from cosalon_oms.oms_order_preferential " +
                "WHERE gmt_create BETWEEN '${DateUtil.beginOfMonth(bookingTime)}' and '${DateUtil.endOfMonth(bookingTime)}' and status in :status" +
                " and coupon_id in :discountIds GROUP by coupon_id) as monthly " +
                "LEFT JOIN (SELECT coupon_id, COUNT(coupon_id) as countWeek from cosalon_oms.oms_order_preferential " +
                "WHERE gmt_create BETWEEN '${DateUtil.beginOfWeek(bookingTime)}' and '${DateUtil.endOfWeek(bookingTime)}' " +
                "and status in :status and coupon_id in :discountIds GROUP by coupon_id) as weekly " +
                "on monthly.coupon_id = weekly.coupon_id LEFT JOIN (SELECT coupon_id, COUNT(coupon_id) as countDay from cosalon_oms.oms_order_preferential " +
                "WHERE gmt_create BETWEEN '${DateUtil.beginOfDay(bookingTime)}' and '${DateUtil.endOfDay(bookingTime)}' " +
                "and status in :status and coupon_id in :discountIds GROUP by coupon_id) as daily on daily.coupon_id = weekly.coupon_id"
        return entityManager.createNativeQuery(query)
                .setParameter("discountIds", discountIds)
                .setParameter("status", status)
                .unwrap(NativeQuery:: class.java)
                .setResultTransformer(Transformers.aliasToBean(OrderDiscountQuantityMaxUsed:: class.java))
                .resultList as List<OrderDiscountQuantityMaxUsed>
    }

    class OrderDiscountQuantityMaxUsed(

        var discountId:BigInteger =BigInteger.ZERO,

        var countMaxDaily:BigInteger =BigInteger.ZERO,

        var countMaxWeekly:BigInteger =BigInteger.ZERO,

        var countMaxMonthly:BigInteger =BigInteger.ZERO
    )
     */

    /**
     *
    @Suppress("UNCHECKED_CAST")
    override fun findAllByWarehouseAndGmtModifiedAndChemicalIds(warehouseId: BigInteger,
                                                                startDate: Date,
                                                                endDate: Date,
                                                                chemicalIds: List<Long>): List<StockTakeDto> {
        val query = "select a.chemical_id            as chemicalId,  " +
                "       b.name                   as chemicalName,  " +
                "       a.chemical_code          as chemicalCode,  " +
                "       c.name                   as unitName,  " +
                "       a.quantity               as quantity,  " +
                "       b.package_unit           as packageUnit,  " +
                "       b.package_weight         as packageWeight,  " +
                "       b.standard_unit          as standardUnit,  " +
                "       c.standard_unit_exchange as standardUnitExchange,  " +
                "       a.gmt_modified           as gmtModified  " +
                "from (select a.warehouse_id, a.chemical_id, a.chemical_code, a.quantity, a.gmt_modified  " +
                "      from ims_warehouse_chemical a  " +
                "      where a.warehouse_id = :warehouseId  " +
                "        and a.gmt_modified between :startDate and :endDate  " +
                "      union all  " +
                "      select b.warehouse_id, b.chemical_id, b.chemical_code, b.quantity, b.gmt_modified  " +
                "      from ims_warehouse_chemical_history b  " +
                "      where b.warehouse_id = :warehouseId  " +
                "        and b.gmt_modified between :startDate and :endDate) a  " +
                "       join ims_chemical b on a.chemical_id = b.id and b.id in :chemicalIds  " +
                "       join ims_chemical_sub_unit c on c.chemical_id = a.chemical_id and c.type = :unitType  " +
                "order by a.gmt_modified asc"
        return entityManager.createNativeQuery(query)
                .setParameter("warehouseId", warehouseId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("chemicalIds", chemicalIds)
                .setParameter("unitType", ChemicalSubUnit.TypeEnum.STORAGE.value)
                .unwrap(NativeQuery::class.java)
                .setResultTransformer(Transformers.aliasToBean(StockTakeDto::class.java))
                .resultList as List<StockTakeDto>
    }
     */
}
