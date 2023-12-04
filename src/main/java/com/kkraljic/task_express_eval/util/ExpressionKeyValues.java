package com.kkraljic.task_express_eval.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExpressionKeyValues {

    CUSTOMER_FIRST_NAME("customer.firstName"),
    CUSTOMER_LAST_NAME("customer.lastName"),
    CUSTOMER_ADDRESS("customer.address"),
    CUSTOMER_ADDRESS_CITY("customer.address.city"),
    CUSTOMER_ADDRESS_ZIP_CODE("customer.address.zipCode"),
    CUSTOMER_ADDRESS_STREET("customer.address.street"),
    CUSTOMER_ADDRESS_HOUSE_NUMBER("customer.address.houseNumber"),
    CUSTOMER_SALARY("customer.salary"),
    CUSTOMER_TYPE("customer.type");

    private final String keyValue;

}
