package com.kkraljic.task_express_eval.service;

import com.kkraljic.task_express_eval.dto.CustomerDto;

public interface ExpressionEvaluatorService {

    boolean evaluateExpression(String expression, CustomerDto inputData);

}
