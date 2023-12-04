package com.kkraljic.task_express_eval.service;

import com.kkraljic.task_express_eval.dto.AddressDto;
import com.kkraljic.task_express_eval.dto.CustomerDto;
import com.kkraljic.task_express_eval.dto.EvaluationDto;
import com.kkraljic.task_express_eval.dto.ExpressionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureDataJpa
public class ExpressionServiceTest {

    @Autowired
    ExpressionService expressionService;

    @Test
    void saveExpression_valid() {
        final ExpressionDto expressionDto = new ExpressionDto();
        expressionDto.setName("test expression");
        expressionDto.setExpression("(customer.firstName == 'JOHN' && customer.salary < 100) OR (customer.address != null && customer.address.city == 'Washington')");

        final UUID id = expressionService.saveExpression(expressionDto);
        Assertions.assertNotNull(id);
    }

    @Test
    @Transactional
    void evaluateExpression_valid() {

        final ExpressionDto expressionDto = new ExpressionDto();
        expressionDto.setName("test expression");
        expressionDto.setExpression("(customer.firstName == 'JOHN' && customer.salary < 100) OR (customer.address != null && customer.address.city == 'Washington')");

        final UUID id = expressionService.saveExpression(expressionDto);

        final EvaluationDto evaluationDto = createEvaluationDto(id);

        final boolean evaluationResult = expressionService.evaluateExpression(evaluationDto);

        Assertions.assertFalse(evaluationResult);
    }

    private EvaluationDto createEvaluationDto(final UUID expressionId) {

        final CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Kristina");
        customerDto.setLastName("KK");

        final AddressDto addressDto = new AddressDto();
        addressDto.setCity("Zagreb");
        addressDto.setStreet("Zg");

        customerDto.setAddress(addressDto);

        return new EvaluationDto(expressionId, customerDto);

    }

}
