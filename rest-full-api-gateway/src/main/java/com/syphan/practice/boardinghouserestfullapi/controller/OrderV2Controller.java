package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.common.rest.util.response.OpenApiWithPageResponse;
import com.syphan.practice.registration.api.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Order controller.
 * @author haimt
 */
@Api(tags = {"Order Controller (version 2)"})
@RestController
@RequestMapping("/api/v2/pos3d/orders")
@Slf4j
public class OrderV2Controller {

  /*@Autowired
  private OrderService orderService;*/

  /**
   * Gets list orders.
   *
   * @return the list orders
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "keyword", dataType = "string", paramType = "query",
          value = "Tên/SĐT khách hàng/mã đơn hàng"),
      @ApiImplicitParam(name = "deliveryStatus", dataType = "enum", paramType = "query",
          value = "Trạng thái giao hàng"),
      @ApiImplicitParam(name = "storeId", dataType = "long", paramType = "query",
          required = true, value = "storeId"),
      @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
          value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
      @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
          value = "Number of records per page.", defaultValue = "15")})
  @GetMapping()
  public ResponseEntity<OpenApiWithPageResponse<User>> getListOrders(
      /*@ApiIgnore Pageable pageable,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) OrderDelivery.DeliveryStatus deliveryStatus,
      @RequestParam Long storeId*/
  ) {
    /*Page<OrderSummaryViewDto> ordersPage = orderService.findAllV2(keyword, deliveryStatus,
        storeId, pageable);*/
    return ResponseEntity
        .ok(new OpenApiWithPageResponse<User>(null));
  }
}

