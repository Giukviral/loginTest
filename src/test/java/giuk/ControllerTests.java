package giuk;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import giuk.dto.LoginDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc // E2E test
public class ControllerTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private static final int JTW_OFFSET = 12;

  @Test
  void test_로그인_테스트_잘못된비번() throws Exception {
    //fail
    String body = objectMapper.writeValueAsString(new LoginDTO("test", "test"));
    String result = mockMvc.perform(post("/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn().getResponse()
        .getContentAsString();
    Assertions.assertEquals("{\"message\":\"un correct id/pw\"}", result);
  }

  @Test
  void test_로그인_테스트_부적절한입력() throws Exception {
    //fail
    String body = objectMapper.writeValueAsString(new LoginDTO("tes", "test"));
    String result = mockMvc.perform(post("/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn().getResponse()
        .getContentAsString();
    Assertions.assertEquals("{\"message\":\"invalid id/pw\"}", result);
  }

  @Test
  void test_권한_테스트_admin() throws Exception {
    //success
    String body = objectMapper.writeValueAsString(new LoginDTO("admin", "adminpw"));
    String jwtToken = mockMvc.perform(post("/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString().substring(JTW_OFFSET);
    String result = mockMvc.perform(
            get("/users/my-info").header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
        .getResponse()
        .getContentAsString();

    JSONObject jsonObject = new JSONObject(result);
    Assertions.assertEquals("admin", jsonObject.getString("username"));
    Assertions.assertEquals("admin@email.com", jsonObject.getString("email"));

    result = mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + jwtToken)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString();
    jsonObject = new JSONObject(result);
    Assertions.assertEquals("admin", jsonObject.getString("username"));
    Assertions.assertEquals("admin@email.com", jsonObject.getString("email"));
  }

  @Test
  void test_권한_테스트_client() throws Exception {

    String body = objectMapper.writeValueAsString(new LoginDTO("client", "clientpw"));
    String jwtToken = mockMvc.perform(post("/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString().substring(JTW_OFFSET);
    String result = mockMvc.perform(
            get("/users/my-info").header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
        .getResponse()
        .getContentAsString();
    JSONObject jsonObject = new JSONObject(result);
    Assertions.assertEquals("client", jsonObject.getString("username"));
    Assertions.assertEquals("client@email.com", jsonObject.getString("email"));
    mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + jwtToken)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
  }
}