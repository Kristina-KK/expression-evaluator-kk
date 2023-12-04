package com.kkraljic.task_express_eval.service;

import com.kkraljic.task_express_eval.dto.EvaluationDto;
import com.kkraljic.task_express_eval.dto.ExpressionDto;

import java.util.UUID;

public interface ExpressionService {

    UUID saveExpression(ExpressionDto expressionDto);

    boolean evaluateExpression(EvaluationDto evaluationDto);

}
