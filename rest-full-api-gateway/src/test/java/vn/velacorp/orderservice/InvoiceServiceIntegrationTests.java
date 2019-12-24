//package vn.velacorp.orderservice;
//
//import static org.junit.Assert.assertTrue;
//
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import vn.velacorp.orderservice.dto.CustomerInvoicePayDto;
//import vn.velacorp.orderservice.dto.CustomerInvoicePayItemDto;
//import vn.velacorp.orderservice.dto.DebtItemDto;
//import vn.velacorp.orderservice.dto.OrderAddDto;
//import vn.velacorp.orderservice.dto.OrderLineAddDto;
//import vn.velacorp.orderservice.entity.CustomerDebt;
//import vn.velacorp.orderservice.entity.Invoice;
//import vn.velacorp.orderservice.entity.Orders;
//import vn.velacorp.orderservice.service.CustomerDebtService;
//import vn.velacorp.orderservice.service.InvoiceService;
//import vn.velacorp.orderservice.service.OrderService;
//
//@Slf4j
//public class InvoiceServiceIntegrationTests extends AbstractProductServiceApplicationTests {
//
//  @Autowired
//  private OrderService orderService;
//
//  @Autowired
//  private CustomerDebtService customerDebtService;
//
//  @Autowired
//  private InvoiceService invoiceService;
//
//  @Test
//  @Transactional
//  @Rollback
//  public void payInvoices_Test1() throws Exception {
//    List<OrderLineAddDto> lineAddDtos = new ArrayList<>();
//
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
//        .totalAmount(new BigDecimal(100000L))
//        .storeId(5L)
//        .createdBy(8L)
//        .prePayment(new BigDecimal(0L))
//        .orderLineList(lineAddDtos)
//        .build();
//
//    Orders orders = orderService.insert(orderAddDto);
//    assertTrue(orders != null);
//    assertTrue(orders.getId() != null);
//    assertTrue(orders.getId() > 0L);
//    assertTrue(orders.getCustomerId() == 175L);
//    assertTrue(orders.getStoreId() == 5L);
//    final List<Invoice> savedInvoices = invoiceService.findAllByOrderId(orders.getId());
//
//    DebtItemDto customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt != null);
//    assertTrue(customerDebt.getCustomerId() != null && customerDebt.getCustomerId() == 175L);
//    assertTrue(customerDebt.getDebtInvoiceDtoList() != null
//        && customerDebt.getDebtInvoiceDtoList().size() > 0);
//    assertTrue(customerDebt.getDebtInvoiceDtoList().size() == savedInvoices.size());
//    assertTrue(customerDebt.getTotalAmount() != null
//            && customerDebt.getTotalAmount().compareTo(new BigDecimal(100000L)) == 0
//            && customerDebt.getDebtAmount() != null
//            && customerDebt.getDebtAmount().compareTo(new BigDecimal(100000L)) == 0);
//
//    CustomerInvoicePayDto payDto = new CustomerInvoicePayDto();
//    payDto.setCustomerId(175L);
//    payDto.setPayDate(LocalDateTime.now().plusDays(2));
//    payDto.setInvoicePayItems(savedInvoices.stream()
//        .map(invoice -> new CustomerInvoicePayItemDto(
//            invoice.getId(),
//            new BigDecimal(40000)
//        )).collect(Collectors.toList()));
//    boolean result = invoiceService.payInvoices(payDto);
//    assertTrue(result);
//
//    customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt != null);
//    assertTrue(customerDebt.getTotalAmount() != null
//        && customerDebt.getTotalAmount().compareTo(new BigDecimal(100000L)) == 0
//        && customerDebt.getDebtAmount() != null
//        && customerDebt.getDebtAmount().compareTo(new BigDecimal(100000L - 40000L)) == 0);
//
//    payDto = new CustomerInvoicePayDto();
//    payDto.setCustomerId(175L);
//    payDto.setPayDate(LocalDateTime.now().plusDays(2));
//    payDto.setInvoicePayItems(savedInvoices.stream()
//        .map(invoice -> new CustomerInvoicePayItemDto(
//            invoice.getId(),
//            new BigDecimal(60000)
//        )).collect(Collectors.toList()));
//    result = invoiceService.payInvoices(payDto);
//    assertTrue(result);
//
//    customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt == null || customerDebt.getDebtAmount() == null
//        || customerDebt.getDebtAmount().compareTo(BigDecimal.ZERO) == 0);
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void payInvoices_Test2() throws Exception {
//    List<OrderLineAddDto> lineAddDtos = new ArrayList<>();
//
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
//    OrderAddDto orderAddDto = OrderAddDto.builder()
//        .orderDate(LocalDateTime.now())
//        .salePersonId(8L)
//        .customerId(175L)
//        .discountAmount(BigDecimal.ZERO)
//        .discountType("fixed")
//        .deliveryCost(BigDecimal.ZERO)
//        .deliveryAddress("HaiMT")
//        .isToWarehouse(true)
//        .totalAmount(new BigDecimal(14000000L))
//        .storeId(5L)
//        .createdBy(8L)
//        .prePayment(new BigDecimal(4000000L))
//        .orderLineList(lineAddDtos)
//        .build();
//
//    Orders orders = orderService.insert(orderAddDto);
//    assertTrue(orders != null);
//    assertTrue(orders.getId() != null);
//    assertTrue(orders.getId() > 0L);
//    assertTrue(orders.getCustomerId() == 175L);
//    assertTrue(orders.getStoreId() == 5L);
//    final List<Invoice> savedInvoices = invoiceService.findAllByOrderId(orders.getId());
//
//    DebtItemDto customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt != null);
//    assertTrue(customerDebt.getCustomerId() != null && customerDebt.getCustomerId() == 175L);
//    assertTrue(customerDebt.getDebtInvoiceDtoList() != null
//        && customerDebt.getDebtInvoiceDtoList().size() > 0);
//    assertTrue(customerDebt.getDebtInvoiceDtoList().size() == savedInvoices.size());
//    assertTrue(customerDebt.getTotalAmount() != null
//            && customerDebt.getTotalAmount().compareTo(new BigDecimal(14000000L)) == 0
//            && customerDebt.getDebtAmount() != null
//            && customerDebt.getDebtAmount().compareTo(new BigDecimal(10000000L)) == 0);
//
//    CustomerInvoicePayDto payDto = new CustomerInvoicePayDto();
//    payDto.setCustomerId(175L);
//    payDto.setPayDate(LocalDateTime.now().plusDays(2));
//    payDto.setInvoicePayItems(savedInvoices.stream()
//        .map(invoice -> new CustomerInvoicePayItemDto(
//            invoice.getId(),
//            new BigDecimal(11000000L)
//        )).collect(Collectors.toList()));
//    boolean result = invoiceService.payInvoices(payDto);
//    assertTrue(result);
//
//    customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt == null || customerDebt.getDebtAmount() == null
//        || customerDebt.getDebtAmount().compareTo(BigDecimal.ZERO) == 0);
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void payInvoices_Test3() throws Exception {
//    List<OrderLineAddDto> lineAddDtos = new ArrayList<>();
//
//    OrderLineAddDto item = new OrderLineAddDto();
//    item.setProductId(91L);
//    item.setUomId(1L);
//    item.setUnitPrice(new BigDecimal(500000));
//    item.setQuantity(2L);
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
//        .totalAmount(new BigDecimal(500000 * 2))
//        .storeId(5L)
//        .createdBy(8L)
//        .prePayment(new BigDecimal(500000))
//        .orderLineList(lineAddDtos)
//        .build();
//
//    // tao order 1
//    Orders orders = orderService.insert(orderAddDto);
//    assertTrue(orders != null);
//    assertTrue(orders.getId() != null);
//    assertTrue(orders.getId() > 0L);
//    assertTrue(orders.getCustomerId() == 175L);
//    assertTrue(orders.getStoreId() == 5L);
//    final List<Invoice> savedInvoices = invoiceService.findAllByOrderId(orders.getId());
//
//    // tao order 2
//    Orders orders2 = orderService.insert(orderAddDto);
//    assertTrue(orders2 != null);
//    assertTrue(orders2.getId() != null);
//    assertTrue(orders2.getId() > 0L);
//    assertTrue(orders2.getCustomerId() == 175L);
//    assertTrue(orders2.getStoreId() == 5L);
//    final List<Invoice> savedInvoices2 = invoiceService.findAllByOrderId(orders2.getId());
//
//    DebtItemDto customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt != null);
//    assertTrue(customerDebt.getCustomerId() != null && customerDebt.getCustomerId() == 175L);
//    assertTrue(customerDebt.getDebtInvoiceDtoList() != null
//        && customerDebt.getDebtInvoiceDtoList().size() > 0);
//    assertTrue(customerDebt.getDebtInvoiceDtoList().size()
//        == savedInvoices.size() + savedInvoices2.size());
//    assertTrue(customerDebt.getTotalAmount() != null
//            && customerDebt.getTotalAmount().compareTo(new BigDecimal(500000 * 4)) == 0
//            && customerDebt.getDebtAmount() != null
//            && customerDebt.getDebtAmount().compareTo(new BigDecimal(500000 * 2)) == 0);
//
//    // pay order 1
//    CustomerInvoicePayDto payDto = new CustomerInvoicePayDto();
//    payDto.setCustomerId(175L);
//    payDto.setPayDate(LocalDateTime.now().plusDays(2));
//    payDto.setInvoicePayItems(savedInvoices.stream()
//        .map(invoice -> new CustomerInvoicePayItemDto(
//            invoice.getId(),
//            new BigDecimal(500000)
//        )).collect(Collectors.toList()));
//    boolean result = invoiceService.payInvoices(payDto);
//    assertTrue(result);
//
//    // pay order 2
//    CustomerInvoicePayDto payDto2 = new CustomerInvoicePayDto();
//    payDto2.setCustomerId(175L);
//    payDto2.setPayDate(LocalDateTime.now().plusDays(2));
//    payDto2.setInvoicePayItems(savedInvoices2.stream()
//        .map(invoice -> new CustomerInvoicePayItemDto(
//            invoice.getId(),
//            new BigDecimal(500000)
//        )).collect(Collectors.toList()));
//    boolean result2 = invoiceService.payInvoices(payDto2);
//    assertTrue(result2);
//
//    customerDebt = customerDebtService.getDebtByCustomerId(175L);
//    assertTrue(customerDebt == null || customerDebt.getDebtAmount() == null
//        || customerDebt.getDebtAmount().compareTo(BigDecimal.ZERO) == 0);
//  }
//}
