package com.kkraljic.task_express_eval.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogicalOperators {

    AND_1("AND"),
    AND_2("&&"),
    OR_1("OR"),
    OR_2("||");

    private final String logicalOperator;

}
