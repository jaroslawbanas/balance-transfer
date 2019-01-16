package pl.jbsoft.money_transfer.controller.rest.transfer;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jbsoft.money_transfer.controller.rest.RestControllerTestHelper;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TransferRestControllerTest {

    private static final String ALL_TRANSFER_JSON = "[{\"transferId\":\"2fa1078f-9c2e-4b22-85c7-3ce11b3c06a1\",\"fromAccountId\":1,\"toAccountId\":2,\"balanceAmount\":10,\"balanceCurrency\":\"USD\",\"transferDate\":\"2018-12-31T23:00:00.000+0000\"}," +
            "{\"transferId\":\"d3bc32a4-21f7-48c6-bd08-af1337dc2b72\",\"fromAccountId\":2,\"toAccountId\":1,\"balanceAmount\":10,\"balanceCurrency\":\"USD\",\"transferDate\":\"2018-12-31T23:00:00.000+0000\"}]";
    private static final String SINGLE_TRANSFER_JSON = "{\"transferId\":\"2fa1078f-9c2e-4b22-85c7-3ce11b3c06a1\",\"fromAccountId\":1,\"toAccountId\":2,\"balanceAmount\":10,\"balanceCurrency\":\"USD\",\"transferDate\":\"2018-12-31T23:00:00.000+0000\"}";
    private static final String CREATE_TRANSFER_JSON = "{\"fromAccountId\":1,\"toAccountId\":2,\"amount\":10,\"currency\":\"USD\"}";
    private static final String CREATE_TRANSFER_JSON_WITH_NEGATIVE_AMOUNT = "{\"fromAccountId\":1,\"toAccountId\":2,\"amount\":-10,\"currency\":\"USD\"}";
    private static final String CREATE_TRANSFER_JSON_WITH_DIFFERENCE_CURRENCY = "{\"fromAccountId\":1,\"toAccountId\":4,\"amount\":10,\"currency\":\"USD\"}";
    private static final String CREATE_TRANSFER_JSON_WITH_TOO_LOW_AMOUNT = "{\"fromAccountId\":3,\"toAccountId\":1,\"amount\":10,\"currency\":\"USD\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_all_transfers() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(TransferRestController.TRANSFER_URL)
                .expectedJson(ALL_TRANSFER_JSON)
                .checkGetRequest();
    }

    @Test
    public void should_return_single_transfer_by_id() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(TransferRestController.TRANSFER_URL)
                .expectedJson(SINGLE_TRANSFER_JSON)
                .parameters(Collections.singletonMap("id", "2fa1078f-9c2e-4b22-85c7-3ce11b3c06a1"))
                .checkGetRequest();
    }

    @Test
    public void should_return_status_ok_with_correct_transfer_action_response() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(TransferRestController.TRANSFER_URL)
                .requestJson(CREATE_TRANSFER_JSON)
                .checkPostRequest()
                .andExpect(jsonPath("$.transferId", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.transferDate", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.fromAccountId", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.toAccountId", CoreMatchers.is(2)))
                .andExpect(jsonPath("$.balanceAmount", CoreMatchers.is(10)))
                .andExpect(jsonPath("$.balanceCurrency", CoreMatchers.is("USD")));

    }

    @Test
    public void should_return_bad_request_for_minus_amount() throws Exception {
        String errorMessage = RestControllerTestHelper.buildForStatus(mockMvc, MockMvcResultMatchers.status().isBadRequest())
                .url(TransferRestController.TRANSFER_URL)
                .requestJson(CREATE_TRANSFER_JSON_WITH_NEGATIVE_AMOUNT)
                .checkPostRequest()
                .andReturn().getResponse().getErrorMessage();

        Assert.assertEquals("Amount transfer should be positive", errorMessage);
    }

    @Test
    public void should_return_bad_request_for_not_same_currency() throws Exception {
        String errorMessage = RestControllerTestHelper.buildForStatus(mockMvc, MockMvcResultMatchers.status().isBadRequest())
                .url(TransferRestController.TRANSFER_URL)
                .requestJson(CREATE_TRANSFER_JSON_WITH_DIFFERENCE_CURRENCY)
                .checkPostRequest()
                .andReturn().getResponse().getErrorMessage();

        Assert.assertEquals("Transfer between difference currency is not supported", errorMessage);
    }

    @Test
    public void should_return_bad_request_for_too_low_amount_for_from_account() throws Exception {
        String errorMessage = RestControllerTestHelper.buildForStatus(mockMvc, MockMvcResultMatchers.status().isBadRequest())
                .url(TransferRestController.TRANSFER_URL)
                .requestJson(CREATE_TRANSFER_JSON_WITH_TOO_LOW_AMOUNT)
                .checkPostRequest()
                .andReturn().getResponse().getErrorMessage();

        Assert.assertEquals("From account amount is too low", errorMessage);
    }
}