package com.kkraljic.task_express_eval.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkraljic.task_express_eval.dto.CustomerDto;
import com.kkraljic.task_express_eval.util.ComparisonOperators;
import com.kkraljic.task_express_eval.util.ExpressionKeyValues;
import com.kkraljic.task_express_eval.util.LogicalOperators;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {

    private ObjectMapper objectMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(ExpressionEvaluatorServiceImpl.class);

    public boolean evaluateExpression(final String expression, final CustomerDto inputData) {
        LOGGER.info("Expression evaluation process started.");
        LOGGER.info("Original expression: " + expression);

        try {
            LOGGER.info("Input data as string: " + objectMapper.writeValueAsString(inputData));
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
        }

        // sort input data and parse original expression for replacing in expression
        final Map<String, Object> sortedInputData = sortInputData(inputData);
        final String parsedOriginalExpression = parseOriginalExpression(sortedInputData, expression);
        LOGGER.info("Parsed original expression: " + parsedOriginalExpression);

        final String removedSingleQuotes = removeSingleQuotes(parsedOriginalExpression);
        final String removedSpace = removeSpaces(removedSingleQuotes);
        final String removedBrackets = removeBrackets(removedSpace);

        // prepare expression for comparison evaluation
        final String replacedLogicalOperators = replaceLogicalOperators(removedBrackets);
        final List<String> expressions = splitByLogicalOperators(replacedLogicalOperators);

        // evaluate expression by comparison operators
        final String parsedByComparisonOperators = parseByComparisonOperators(expressions, removedSpace);
        LOGGER.info("Expression evaluated by comparison operators: " + parsedByComparisonOperators);
        LOGGER.info("Before evaluation by logical operators and final result.");

        // evaluate expression by logical operators
        return parseByLogicalOperators(parsedByComparisonOperators);
    }

    private Map<String, Object> sortInputData(final CustomerDto originalJson) {

        final Map<String, Object> map = new HashMap<>();

        map.put(ExpressionKeyValues.CUSTOMER_FIRST_NAME.getKeyValue(), originalJson.getFirstName());
        map.put(ExpressionKeyValues.CUSTOMER_LAST_NAME.getKeyValue(), originalJson.getLastName());

        map.put(ExpressionKeyValues.CUSTOMER_ADDRESS.getKeyValue(), (originalJson.getAddress() == null) ? null : "notNull");
        map.put(ExpressionKeyValues.CUSTOMER_ADDRESS_CITY.getKeyValue(), (originalJson.getAddress() == null) ? null : originalJson.getAddress().getCity());
        map.put(ExpressionKeyValues.CUSTOMER_ADDRESS_ZIP_CODE.getKeyValue(), (originalJson.getAddress() == null) ? null : originalJson.getAddress().getZipCode());
        map.put(ExpressionKeyValues.CUSTOMER_ADDRESS_STREET.getKeyValue(), (originalJson.getAddress() == null) ? null : originalJson.getAddress().getStreet());
        map.put(ExpressionKeyValues.CUSTOMER_ADDRESS_HOUSE_NUMBER.getKeyValue(), (originalJson.getAddress() == null) ? null : originalJson.getAddress().getHouseNumber());

        map.put(ExpressionKeyValues.CUSTOMER_SALARY.getKeyValue(), (originalJson.getSalary() == null) ? 0 : originalJson.getSalary());
        map.put(ExpressionKeyValues.CUSTOMER_TYPE.getKeyValue(), originalJson.getType());

        return map;

    }

    private String parseOriginalExpression(final Map<String, Object> sortedOriginalJson, final String originalExpression) {

        String replaced = originalExpression;

        for (Map.Entry<String, Object> entry : sortedOriginalJson.entrySet()) {
            replaced = replaced.replace( entry.getKey() + " ", entry.getValue() == null ? "null" : entry.getValue().toString() + " ");
        }

        return replaced;
    }

    private String removeSingleQuotes(final String expression) {
        return expression.replaceAll("'", "");
    }

    private String removeSpaces(final String expression) {
        return expression.replace(" ", "");
    }

    private String removeBrackets(final String expression) {
        return expression
                .replace("(", "")
                .replace(")", "");
    }

    private String replaceLogicalOperators(final String expression) {
        return expression
                .replace("&&", "-")
                .replace("AND", "-")
                .replace("||", "-")
                .replace("OR", "-");
    }

    private List<String> splitByLogicalOperators(final String expression) {
        return Arrays.asList(expression.split("-"));
    }

    private String parseByComparisonOperators(List<String> expressions, String expression) {

        // evaluate comparison expressions and replace them in original expression
        String originalExpression = expression;

        for(String e : expressions) {

            boolean comparisonResult = getComparisonExpressionResult(e);

            originalExpression = originalExpression.replace(e, String.valueOf(comparisonResult));

        }

        return originalExpression;

    }

    private boolean getComparisonExpressionResult(final String expression) {

        boolean comparisonResult = false;

        if(expression.contains(ComparisonOperators.EQUAL.getComparisonOperator())) {

            final String[] comparisonValues = expression.split(ComparisonOperators.EQUAL.getComparisonOperator());
            comparisonResult = comparisonValues[0].equalsIgnoreCase(comparisonValues[1]);

        }

        if(expression.contains(ComparisonOperators.NOT_EQUAL.getComparisonOperator())) {

            final String[] comparisonValues = expression.split(ComparisonOperators.NOT_EQUAL.getComparisonOperator());
            comparisonResult = !comparisonValues[0].equalsIgnoreCase(comparisonValues[1]);

        }

        if(expression.contains(ComparisonOperators.GREATER.getComparisonOperator())) {

            final String[] comparisonValues = expression.split(ComparisonOperators.GREATER.getComparisonOperator());
            comparisonResult = Integer.parseInt(comparisonValues[0]) > Integer.parseInt((comparisonValues[1]));

        }

        if(expression.contains(ComparisonOperators.LESS.getComparisonOperator())) {

            final String[] comparisonValues = expression.split(ComparisonOperators.LESS.getComparisonOperator());
            comparisonResult = Integer.parseInt(comparisonValues[0]) < Integer.parseInt((comparisonValues[1]));

        }

        if(expression.contains(ComparisonOperators.GREATER_THAN_OR_EQUAL.getComparisonOperator())) {

            final String[] comparisonValues = expression.split(ComparisonOperators.GREATER_THAN_OR_EQUAL.getComparisonOperator());
            comparisonResult = Integer.parseInt(comparisonValues[0]) >= Integer.parseInt((comparisonValues[1]));

        }

        if(expression.contains(ComparisonOperators.LESS_THAN_OR_EQUAL.getComparisonOperator())) {

            final String[] comparisonValues = expression.split(ComparisonOperators.LESS_THAN_OR_EQUAL.getComparisonOperator());
            comparisonResult = Integer.parseInt(comparisonValues[0]) >= Integer.parseInt((comparisonValues[1]));

        }

        return comparisonResult;

    }

    private boolean parseByLogicalOperators(final String expression) {

        String originalExpression = expression;

        int openedBracket = originalExpression.indexOf("(");
        int closedBracket = originalExpression.indexOf(")", openedBracket + 1);

        if(openedBracket == -1 && originalExpression.length() <= 5) {
            return Boolean.parseBoolean(originalExpression);
        }

        String subExpression = "";

        if(openedBracket == -1) {
            subExpression = originalExpression;
        } else {
            subExpression = originalExpression.substring(openedBracket + 1, closedBracket);
        }

        boolean result = getLogicalExpressionResult(subExpression);

        if(openedBracket == -1) {
            originalExpression = originalExpression.replace( subExpression, String.valueOf(result));
        } else {
            originalExpression = originalExpression.replace("(" + subExpression + ")", String.valueOf(result));
        }

        return parseByLogicalOperators(originalExpression);

    }

    private boolean getLogicalExpressionResult(final String subExpression) {

        boolean result = false;

        if(subExpression.contains(LogicalOperators.AND_1.getLogicalOperator())) {

            final String[] values = subExpression.split(LogicalOperators.AND_1.getLogicalOperator());
            result = Boolean.parseBoolean(values[0]) && Boolean.parseBoolean((values[1]));

        }

        if(subExpression.contains(LogicalOperators.AND_2.getLogicalOperator())) {

            final String[] values = subExpression.split(LogicalOperators.AND_2.getLogicalOperator());
            result = Boolean.parseBoolean(values[0]) && Boolean.parseBoolean((values[1]));

        }

        if(subExpression.contains(LogicalOperators.OR_1.getLogicalOperator())) {

            final String[] values = subExpression.split(LogicalOperators.OR_1.getLogicalOperator());
            result = Boolean.parseBoolean(values[0]) || Boolean.parseBoolean((values[1]));

        }

        if(subExpression.contains(LogicalOperators.OR_2.getLogicalOperator())) {

            final String[] values = subExpression.split(LogicalOperators.OR_2.getLogicalOperator());
            result = Boolean.parseBoolean(values[0]) || Boolean.parseBoolean((values[1]));

        }

        return result;

    }

}