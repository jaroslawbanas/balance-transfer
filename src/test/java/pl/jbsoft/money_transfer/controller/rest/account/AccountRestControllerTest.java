package pl.jbsoft.money_transfer.controller.rest.account;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jbsoft.money_transfer.business.account.Account;
import pl.jbsoft.money_transfer.controller.date.DateProvider;
import pl.jbsoft.money_transfer.controller.repository.AccountRepository;
import pl.jbsoft.money_transfer.controller.rest.RestControllerTestHelper;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountRestControllerTest {

    private static final String ALL_ACCOUNT_JSON = "[{\"id\": 1,\"mainOwnerId\": 1,\"balanceAmount\": 0,\"balanceCurrency\": \"USD\",\"creationDate\": \"2018-12-31T23:00:00.000+0000\"}," +
            "{\"id\": 2,\"mainOwnerId\": 2, \"balanceAmount\": 0,\"balanceCurrency\": \"USD\",\"creationDate\": \"2018-12-31T23:00:00.000+0000\"}," +
            "{\"id\": 3,\"mainOwnerId\": 3,\"balanceAmount\": 0,\"balanceCurrency\": \"USD\",\"creationDate\": \"2018-12-31T23:00:00.000+0000\"}]";

    private static final String SINGLE_ACCOUNT_JSON = "{\"id\": 1,\"mainOwnerId\": 1, \"balanceAmount\": 0, \"balanceCurrency\": \"USD\", \"creationDate\": \"2018-12-31T23:00:00.000+0000\"}";
    private static final String CREATE_ACCOUNT_JSON_REQUEST = "{\"mainOwnerId\": 2, \"currency\": \"USD\"}";
    private static final String CREATE_INVALID_ACCOUNT_JSON_REQUEST = "{\"mainOwnerId\": 6, \"currency\": \"USD\"}";
    private static final String CREATE_ACCOUNT_JSON_RESPONSE = "{\"id\": 4,\"mainOwnerId\": 2, \"balanceAmount\": 0, \"balanceCurrency\": \"USD\", \"creationDate\": \"2018-12-31T23:00:00.000+0000\"},";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DateProvider dateProvider;

    @Before
    public void init() throws ParseException {
        Mockito.when(dateProvider.getCurrentDate())
                .thenReturn(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));
    }

    @Test
    public void should_return_all_accounts() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(AccountRestController.ACCOUNT_URL)
                .expectedJson(ALL_ACCOUNT_JSON)
                .checkGetRequest();
    }

    @Test
    public void should_return_single_account_by_id() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(AccountRestController.ACCOUNT_URL)
                .expectedJson(SINGLE_ACCOUNT_JSON)
                .parameters(Collections.singletonMap("id", "1"))
                .checkGetRequest();

    }

    @Test
    public void should_create_account_form_request() throws Exception {
        RestControllerTestHelper.buildForStatusOk(mockMvc)
                .url(AccountRestController.ACCOUNT_URL)
                .requestJson(CREATE_ACCOUNT_JSON_REQUEST)
                .expectedJson(CREATE_ACCOUNT_JSON_RESPONSE)
                .checkPostRequest();
    }

    @Test
    public void should_return_bad_request_for_not_exits_owner() throws Exception {
        RestControllerTestHelper.buildForStatus(mockMvc, MockMvcResultMatchers.status().isBadRequest())
                .url(AccountRestController.ACCOUNT_URL)
                .requestJson(CREATE_INVALID_ACCOUNT_JSON_REQUEST)
                .checkPostRequest();
    }
}