package com.kkraljic.task_express_eval.rest;

import com.kkraljic.task_express_eval.dto.EvaluationDto;
import com.kkraljic.task_express_eval.dto.ExpressionDto;
import com.kkraljic.task_express_eval.service.ExpressionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class ExpressionEvaluatorController {

    private final Logger LOGGER = LoggerFactory.getLogger(ExpressionEvaluatorController.class);

    private ExpressionService expressionService;

    @PostMapping(value = "/expression", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> saveExpression(@RequestBody @Valid ExpressionDto expressionDto) {
        LOGGER.info("REST request to save expression.");
        final UUID savedExpressionId = expressionService.saveExpression(expressionDto);
        return ResponseEntity.ok(savedExpressionId);
    }

    @PostMapping(value = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> evaluateExpression(@RequestBody @Valid EvaluationDto evaluationDto) {
        LOGGER.info("REST request to evaluate expression.");
        final boolean evaluationResult = expressionService.evaluateExpression(evaluationDto);
        return ResponseEntity.ok(evaluationResult);
    }

}
