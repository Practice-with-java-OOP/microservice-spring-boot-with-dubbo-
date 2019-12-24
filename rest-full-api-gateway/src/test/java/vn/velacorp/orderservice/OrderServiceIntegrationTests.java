//package vn.velacorp.orderservice;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import vn.velacorp.commonpersistencebase.entity.BaseEntity;
//import vn.velacorp.orderservice.dto.DebtInvoiceDto;
//import vn.velacorp.orderservice.dto.InvoiceScheduledPayDateChangeDto;
//import vn.velacorp.orderservice.dto.OrderAddDto;
//import vn.velacorp.orderservice.dto.OrderLineAddDto;
//import vn.velacorp.orderservice.dto.OrderStatusChangeDto;
//import vn.velacorp.orderservice.entity.CustomerDebt;
//import vn.velacorp.orderservice.entity.Invoice;
//import vn.velacorp.orderservice.entity.OrderDelivery;
//import vn.velacorp.orderservice.entity.Orders;
//import vn.velacorp.orderservice.repository.CustomerDebtRepository;
//import vn.velacorp.orderservice.repository.InvoiceRepository;
//import vn.velacorp.orderservice.repository.OrderRepository;
//import vn.velacorp.orderservice.service.InvoiceService;
//import vn.velacorp.orderservice.service.OrderService;
//
//@Slf4j
//public class OrderServiceIntegrationTests extends AbstractProductServiceApplicationTests {
//
//  @Autowired
//  private OrderRepository orderRepository;
//
//  @Autowired
//  private OrderService orderService;
//
//  @Autowired
//  private CustomerDebtRepository customerDebtRepository;
//
//  @Autowired
//  private InvoiceService invoiceService;
//
//  @Autowired
//  private InvoiceRepository invoiceRepository;
//
//  @Test
//  @Transactional
//  @Rollback
//  public void insertTest1() throws Exception {
//    List<OrderLineAddDto> lineAddDtos = new ArrayList<>();
//    OrderLineAddDto item = new OrderLineAddDto();
//    item.setProductId(91L);
//    item.setUomId(1L);
//    item.setUnitPrice(new BigDecimal(350000));
//    item.setQuantity(40L);
//    item.setDiscountType("fixed");
//    item.setDiscountAmount(BigDecimal.ZERO);
//    item.setPickFrom("kho");
//    item.setBaseUomQty(0L);
//    item.setExchangeUomQty(1L);
//    lineAddDtos.add(item);
//
//    item = new OrderLineAddDto();
//    item.setProductId(92L);
//    item.setUomId(1L);
//    item.setUnitPrice(new BigDecimal(320000));
//    item.setQuantity(39L);
//    item.setDiscountType("fixed");
//    item.setDiscountAmount(BigDecimal.ZERO);
//    item.setPickFrom("kho");
//    item.setBaseUomQty(0L);
//    item.setExchangeUomQty(1L);
//    lineAddDtos.add(item);
//
//    OrderAddDto orderAddDto = OrderAddDto.builder()
//        .orderDate(LocalDateTime.now())
//        .salePersonId(8L)
//        .customerId(175L)
//        .discountAmount(BigDecimal.ZERO)
//        .discountType("fixed")
//        .deliveryCost(BigDecimal.ZERO)
//        .deliveryAddress("HaiMT")
//        .isToWarehouse(true)
//        .totalAmount(new BigDecimal(1000000L))
//        .storeId(5L)
//        .createdBy(8L)
//        .prePayment(new BigDecimal(100000L))
//        .orderLineList(lineAddDtos)
//        .build();
//
//    Orders orders = orderService.insert(orderAddDto);
//    assertTrue(orders != null);
//    assertTrue(orders.getId() != null);
//    assertTrue(orders.getId() > 0L);
//    assertTrue(orders.getCustomerId() == 175L);
//    assertTrue(orders.getStoreId() == 5L);
//
//    List<CustomerDebt> allCustomerDebts = customerDebtRepository.findAll();
//    log.info("allCustomerDebts:{}", allCustomerDebts);
//    assertTrue(allCustomerDebts != null && allCustomerDebts.size() > 0);
//    assertTrue(allCustomerDebts.stream().anyMatch(customerDebt ->
//        customerDebt.getCustomerId() == 175L
//            && customerDebt.getTotalAmount() != null
//            && customerDebt.getTotalAmount().compareTo(new BigDecimal(1000000L)) == 0
//            && customerDebt.getDebtAmount() != null
//            && customerDebt.getDebtAmount().compareTo(new BigDecimal(1000000L - 100000L)) == 0)
//    );
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void invoice_UpdateScheduledPayDate_Test1() throws Exception {
//    LocalDate today = LocalDate.now();
//    LocalDate time = today.plusDays(2);
//    final DebtInvoiceDto saved = invoiceService.updateScheduledPayDate(150L,
//        new InvoiceScheduledPayDateChangeDto(time));
//    assertTrue(saved != null);
//    assertTrue(saved.getInvoiceId() == 150L);
//    assertTrue(saved.getScheduledPayAt() != null
//        && LocalDate.from(saved.getScheduledPayAt()).compareTo(time) == 0);
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void invoice_UpdateScheduledPayDate_Test2() throws Exception {
//    LocalDate today = LocalDate.now();
//    LocalDate time = today.plusDays(-2);
//    final DebtInvoiceDto saved = invoiceService.updateScheduledPayDate(150L,
//        new InvoiceScheduledPayDateChangeDto(time));
//    assertTrue(saved != null);
//    assertTrue(saved.getInvoiceId() == 150L);
//    assertTrue(saved.getScheduledPayAt() != null
//        && LocalDate.from(saved.getScheduledPayAt()).compareTo(time) == 0);
//    assertTrue(saved.getExpiredDays() == 2L);
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void cancelOrder_Test1() throws Exception {
////    final List<Invoice> befores = invoiceRepository.findAllByOrdersId(152L)
////        .stream()
////        .filter(invoice -> invoice.getIsActive())
////        .collect(Collectors.toList());
//    final OrderDelivery orderDelivery = orderService.changeDeliveryStatus(152L,
//        new OrderStatusChangeDto(OrderDelivery.DeliveryStatus.CANCELED));
//    final List<Invoice> afters = invoiceRepository.findAllByOrdersId(152L)
//        .stream()
//        .filter(BaseEntity::getIsActive)
//        .collect(Collectors.toList());
//    assertTrue(afters.isEmpty());
//    assertTrue(orderDelivery != null && orderDelivery.getDeliveryStatus() != null
//        && orderDelivery.getDeliveryStatus().equalsIgnoreCase(
//            OrderDelivery.DeliveryStatus.CANCELED.getValue()));
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void cancelOrder_Test2() throws Exception {
////    final List<Invoice> befores = invoiceRepository.findAllByOrdersId(152L)
////        .stream()
////        .filter(invoice -> invoice.getIsActive())
////        .collect(Collectors.toList());
//    final OrderDelivery orderDelivery = orderService.changeDeliveryStatus(151L,
//        new OrderStatusChangeDto(OrderDelivery.DeliveryStatus.CANCELED));
//    final List<Invoice> afters = invoiceRepository.findAllByOrdersId(151L)
//        .stream()
//        .filter(BaseEntity::getIsActive)
//        .collect(Collectors.toList());
//    assertTrue(afters.isEmpty());
//    assertTrue(orderDelivery != null && orderDelivery.getDeliveryStatus() != null
//        && orderDelivery.getDeliveryStatus().equalsIgnoreCase(
//            OrderDelivery.DeliveryStatus.CANCELED.getValue()));
//  }
//
//  @Test
//  public void getListCustomerDebtTest() throws Exception {
//    MvcResult mvcResult = mvc.perform(
//        MockMvcRequestBuilders.get("/api/v1/pos3d/customer-debt/all?storeId={id}",
//            1)
//            .accept(MediaType.ALL))
//        .andReturn();
//
//    assertEquals(200, mvcResult.getResponse().getStatus());
//    assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
//  }
//
//  @Test
//  public void getCustomerDebtByCustomerIdTest() throws Exception {
//    MvcResult mvcResult = mvc.perform(
//        MockMvcRequestBuilders.get("/api/v1/pos3d/customer-debt/by-customer/{id}",
//            21)
//            .accept(MediaType.ALL))
//        .andReturn();
//
//    assertEquals(200, mvcResult.getResponse().getStatus());
//    assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
//  }
//}
