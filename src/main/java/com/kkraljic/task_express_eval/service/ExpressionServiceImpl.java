package com.kkraljic.task_express_eval.service;

import com.kkraljic.task_express_eval.dto.EvaluationDto;
import com.kkraljic.task_express_eval.dto.ExpressionDto;
import com.kkraljic.task_express_eval.entity.Expression;
import com.kkraljic.task_express_eval.mapper.ExpressionMapper;
import com.kkraljic.task_express_eval.repository.ExpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExpressionServiceImpl implements ExpressionService {

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ExpressionMapper expressionMapper;

    @Autowired
    private ExpressionEvaluatorService evaluatorService;

    public UUID saveExpression(final ExpressionDto expressionDto) {
        final Expression expression = expressionMapper.dtoToEntity(expressionDto);
        final Expression savedExpression = expressionRepository.save(expression);

        return savedExpression.getId();
    }


    public boolean evaluateExpression(final EvaluationDto evaluationDto) {
        final Expression expression = expressionRepository.getReferenceById(evaluationDto.getExpressionId());

        return evaluatorService.evaluateExpression(expression.getExpression(), evaluationDto.getCustomer());
    }

}
