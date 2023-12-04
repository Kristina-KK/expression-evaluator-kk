package com.kkraljic.task_express_eval.service;

import com.kkraljic.task_express_eval.dto.AddressDto;
import com.kkraljic.task_express_eval.dto.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExpressionEvaluatorServiceTest {

    @Autowired
    ExpressionEvaluatorService expressionEvaluatorService;

    @Test
    void evaluateExpression_true() {

        final String expression = "(customer.firstName == 'Kristina' && customer.salary < 100) OR (customer.address != null && customer.address.city == 'Zagreb')";
        final CustomerDto customer = createCustomer();

        Assertions.assertTrue(expressionEvaluatorService.evaluateExpression(expression, customer));
    }

    @Test
    void evaluateExpression_false() {

        final String expression = "(customer.firstName == 'JOHN' && customer.salary < 100) OR (customer.address != null && customer.address.city == 'Washington')";
        final CustomerDto customer = createCustomer();

        Assertions.assertFalse(expressionEvaluatorService.evaluateExpression(expression, customer));
    }

    private CustomerDto createCustomer() {

        final CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Kristina");
        customerDto.setLastName("KK");

        final AddressDto addressDto = new AddressDto();
        addressDto.setCity("Zagreb");
        addressDto.setStreet("Zg");

        customerDto.setAddress(addressDto);

        return customerDto;
    }

}
