package com.kkraljic.task_express_eval;

import com.kkraljic.task_express_eval.exception.RestResponseEntityExceptionHandler;
import com.kkraljic.task_express_eval.mapper.ExpressionMapper;
import com.kkraljic.task_express_eval.repository.ExpressionRepository;
import com.kkraljic.task_express_eval.rest.ExpressionEvaluatorController;
import com.kkraljic.task_express_eval.service.ExpressionEvaluatorService;
import com.kkraljic.task_express_eval.service.ExpressionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpressionEvaluatorAppTest {

	@Autowired
	ExpressionMapper expressionMapper;

	@Autowired
	ExpressionRepository expressionRepository;

	@Autowired
	ExpressionService expressionService;

	@Autowired
	ExpressionEvaluatorService expressionEvaluatorService;

	@Autowired
	ExpressionEvaluatorController expressionEvaluatorController;

	@Autowired
	RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(expressionMapper);
		Assertions.assertNotNull(expressionRepository);
		Assertions.assertNotNull(expressionEvaluatorService);
		Assertions.assertNotNull(expressionEvaluatorController);
		Assertions.assertNotNull(restResponseEntityExceptionHandler);
	}

}
