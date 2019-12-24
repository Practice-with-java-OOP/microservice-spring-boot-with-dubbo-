//package vn.velacorp.orderservice;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import javax.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OrderServiceApplication.class)
//@ActiveProfiles("dev")
//@Slf4j
//public abstract class AbstractProductServiceApplicationTests {
//
//  protected MockMvc mvc;
//
//  @Autowired
//  WebApplicationContext webApplicationContext;
//
//  @PostConstruct
//  public void init() {
//    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    log.info("mvc {}", mvc);
//  }
//
//  protected String mapToJson(Object obj) throws JsonProcessingException {
//    ObjectMapper objectMapper = new ObjectMapper();
//    return objectMapper.writeValueAsString(obj);
//  }
//
//  protected <T> T mapFromJson(String json, Class<T> clazz)
//      throws JsonParseException, JsonMappingException, IOException {
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    return objectMapper.readValue(json, clazz);
//  }
//
//  protected <T> T mapFromJson(String json, TypeReference<T> typeReference)
//      throws JsonParseException, JsonMappingException, IOException {
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    return objectMapper.readValue(json, typeReference);
//  }
//}
