//package vn.velacorp.orderservice;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@Slf4j
//public class InvoiceListServiceTest extends AbstractProductServiceApplicationTests {
//
//    @Test
//    public void getPageInvoiceTest1() throws Exception {
//        MvcResult mvcResult = mvc.perform(
//                MockMvcRequestBuilders.get("/api/v1/pos3d/invoice?storeId={id}",
//                        131)
//                        .accept(MediaType.ALL))
//                .andReturn();
//
//        assertEquals(200, mvcResult.getResponse().getStatus());
//        assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
//    }
//
//    @Test
//    public void getPageInvoiceTestWithKeyword() throws Exception {
//        MvcResult mvcResult = mvc.perform(
//                MockMvcRequestBuilders.get("/api/v1/pos3d/invoice?storeId={id}&keyword={keyword}",
//                        131, "Long")
//                        .accept(MediaType.ALL))
//                .andReturn();
//
//        assertEquals(200, mvcResult.getResponse().getStatus());
//        assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
//    }
//
//    @Test
//    public void getPageInvoiceTestWithOrderRef() throws Exception {
//        MvcResult mvcResult = mvc.perform(
//                MockMvcRequestBuilders.get("/api/v1/pos3d/invoice?storeId={id}&keyword={keyword}&orderRef={}",
//                        131, "Long", "DH00000269")
//                        .accept(MediaType.ALL))
//                .andReturn();
//
//        assertEquals(200, mvcResult.getResponse().getStatus());
//        assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
//    }
//
//    @Test
//    public void getPageInvoiceTestWithInvoiceRef() throws Exception {
//        MvcResult mvcResult = mvc.perform(
//                MockMvcRequestBuilders.get("/api/v1/pos3d/invoice?storeId={id}&keyword={keyword}&orderRef={invoiceRef}&invoiceRef={invoiceRef}",
//                        131, "Long", "DH00000269", "inv0423423")
//                        .accept(MediaType.ALL))
//                .andReturn();
//
//        assertEquals(200, mvcResult.getResponse().getStatus());
//        assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
//    }
//}
