package com.kkraljic.task_express_eval.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkraljic.task_express_eval.ExpressionEvaluatorApp;
import com.kkraljic.task_express_eval.dto.ExpressionDto;
import com.kkraljic.task_express_eval.repository.ExpressionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExpressionEvaluatorApp.class)
@AutoConfigureMockMvc
public class ExpressionEvaluatorControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ExpressionRepository expressionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void saveExpression_valid() throws Exception {

        final ExpressionDto expressionDto = new ExpressionDto();
        expressionDto.setName("test name");
        expressionDto.setExpression("(customer.firstName == 'JOHN' && customer.salary < 100) OR (customer.address != null && customer.address.city == 'Washington')");

        final String expressionDtoAsString = objectMapper.writeValueAsString(expressionDto);

        final MvcResult mvcResult = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionDtoAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        final String response = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(isValidJson(response, UUID.class));
    }

    private boolean isValidJson(final String json, final Class clazz) {
        try {
            objectMapper.readValue(json, clazz);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }

}
