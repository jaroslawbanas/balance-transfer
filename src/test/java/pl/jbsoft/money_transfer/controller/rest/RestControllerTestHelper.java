package pl.jbsoft.money_transfer.controller.rest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestControllerTestHelper {

    private String url;
    private String expectedJson;
    private String requestJson;
    private MockMvc mockMvc;
    private Map<String, String> parameters = new HashMap<>();
    private ResultMatcher resultMatcher;

    public static RestControllerTestHelper buildForStatusOk(MockMvc mockMvc) {
        return buildForStatus(mockMvc, MockMvcResultMatchers.status().isOk());
    }

    public static RestControllerTestHelper buildForStatus(MockMvc mockMvc, ResultMatcher resultMatcher) {
        RestControllerTestHelper restControllerTestHelper = new RestControllerTestHelper();
        restControllerTestHelper.mockMvc = mockMvc;
        restControllerTestHelper.resultMatcher = resultMatcher;

        return restControllerTestHelper;
    }

    public RestControllerTestHelper url(String url) {
        this.url = url;
        return this;
    }

    public RestControllerTestHelper expectedJson(String expectedJson) {
        this.expectedJson = expectedJson;
        return this;
    }

    public RestControllerTestHelper requestJson(String requestJson) {
        this.requestJson = requestJson;
        return this;
    }

    public RestControllerTestHelper parameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public ResultActions checkGetRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url);
        return addContentAndPerform(builder);
    }

    public ResultActions checkPostRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url);
        return addContentAndPerform(builder);
    }

    public ResultActions checkPutRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(url);
        return addContentAndPerform(builder);
    }

    private ResultActions addContentAndPerform(MockHttpServletRequestBuilder builder) throws Exception {
        parameters.forEach(builder::param);

        if (Objects.nonNull(requestJson)) {
            builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestJson);
        }

        ResultActions perform = mockMvc.perform(builder);

        if (Objects.nonNull(expectedJson)) {
            ResultMatcher contentMatcher = MockMvcResultMatchers
                    .content()
                    .json(expectedJson);
            perform.andExpect(contentMatcher);
        }

        return perform.andExpect(resultMatcher);
    }
}
