package pl.jbsoft.money_transfer.controller.rest.owner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.jbsoft.money_transfer.controller.rest.RestControllerTestHelper;

import javax.transaction.Transactional;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OwnerRestControllerTest {

    //TODO move it to files
    private static final String ALL_OWNERS_JSON = "[{\"id\":1,\"name\":\"Jon\"},{\"id\":2,\"name\":\"Alice\"},{\"id\":3,\"name\":\"Ben\"}]";
    private static final String SINGLE_OWNER_JSON = "{\"id\":1,\"name\":\"Jon\"}";
    private static final String NEW_OWNER_JSON_REQUEST = "{\"ownerName\":\"TEST\"}";
    private static final String NEW_OWNER_JSON_RESPONSE = "{\"id\":4,\"name\":\"TEST\"}";
    private static final String UPDATE_OWNER_JSON_REQUEST = "{\"id\":3,\"name\":\"TEST\"}";
    private static final String UPDATE_OWNER_JSON_RESPONSE = "{\"id\":3,\"name\":\"TEST\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_all_owners() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(OwnerRestController.OWNERS_URL)
                .expectedJson(ALL_OWNERS_JSON)
                .checkGetRequest();
    }

    @Test
    public void should_return_single_owner_t_by_id() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(OwnerRestController.OWNERS_URL)
                .expectedJson(SINGLE_OWNER_JSON)
                .parameters(Collections.singletonMap("id", "1"))
                .checkGetRequest();
    }

    @Test
    public void should_create_owner_for_provided_name() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(OwnerRestController.OWNERS_URL)
                .expectedJson(NEW_OWNER_JSON_RESPONSE)
                .requestJson(NEW_OWNER_JSON_REQUEST)
                .checkPostRequest();
    }

    @Test
    public void should_update_owner_name_by_provided_in_request_id() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(OwnerRestController.OWNERS_URL)
                .expectedJson(UPDATE_OWNER_JSON_RESPONSE)
                .requestJson(UPDATE_OWNER_JSON_REQUEST)
                .checkPutRequest();
    }
}